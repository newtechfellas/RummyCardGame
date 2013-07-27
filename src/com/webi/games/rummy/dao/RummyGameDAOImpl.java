package com.webi.games.rummy.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.webi.ent.registration.UserRegistryEntity;
import com.webi.games.rummy.entity.MailManagerEntity;
import com.webi.games.rummy.entity.RummyGameAssociatedPlayersEntity;
import com.webi.games.rummy.entity.RummyGameEntity;
import com.webi.games.rummy.entity.RummyGameHandPositionEntity;
import com.webi.games.rummy.game.EmptyNewGameExistsException;
import com.webi.games.rummy.game.IPlayer;
import com.webi.games.rummy.game.IRummyGame;
import com.webi.games.rummy.game.Player;
import com.webi.games.rummy.game.RummyGameBO;

// TODO: None of these methods should own the transaction. Transaction should be started @ service level and
// all these methods should expect the existing transaction and use it
@Repository
public class RummyGameDAOImpl implements IRummyGameDAO {
	
	private final static String HQL_OPEN_GAMES_FOR_PLAYER = "FROM RummyGameEntity p WHERE p.originatorPlayerID=:originatorPlayerId AND ( isCompleted=false OR isTerminated=false ) " ;
	
	// Use a right join between RummyGameTbl and RummyGameAssociatedPlayers_Tbl
	//TOOD: Bad query.. should not use UserRegistryEntity. Consider using PlayerPersonalInfoEntity  
	private final static String HQL_OPEN_GAMES_INVITED_FOR_PLAYER = "FROM RummyGameEntity  game , RummyGameAssociatedPlayersEntity assocPlayers, " +
			" UserRegistryEntity playerInfo WHERE game.gameId=assocPlayers.gameId AND game.originatorPlayerID != :playerId AND assocPlayers.playerId=:playerId " +
			"AND playerInfo.userId=game.originatorPlayerID";
	private final static String HQL_GAMES_HISTORY_FOR_PLAYER = "FROM RummyGameEntity p WHERE p.originatorPlayerID=:originatorPlayerId AND ( isCompleted=true OR isTerminated=true ";
	private final static String HQL_NOT_STARTED_GAME_FOR_PLAYER = "FROM RummyGameEntity p WHERE p.originatorPlayerID=:originatorPlayerId AND isActive=false";
	
	private final static String HQL_GET_PLAYER_NAMES_FROM_IDS = "From UserRegistryEntity playerInfo WHERE playerInfo.userId in (:playerIds )";
	

	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	private DataSource datasource;
	
	/* (non-Javadoc)
	 * @see com.webi.games.rummy.dao.IRummyGameDAO#createNewGame(java.lang.String, com.webi.games.rummy.game.IPlayer, java.util.Set)
	 */
	@Override
	public RummyGameEntity createNewGame(String gameName, IPlayer player, final Set<IPlayer> friendsSet)  throws EmptyNewGameExistsException {
		
//		RummyGameEntity openGame= getNotStartedOpenGame(player);
//		if ( openGame != null ) {
//			throw new EmptyNewGameExistsException();
//		}
		
		final RummyGameEntity rummyGameEntity = createNewRummyEntityObject(gameName, player);
		
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		tx.execute(new TransactionCallbackWithoutResult() {
			
		    public void doInTransactionWithoutResult(TransactionStatus status) {
			    	//Create the new game entry
				Long gameID = (Long)sessionFactory.getCurrentSession().save(rummyGameEntity);
				
				//Create Associations to the newly created game
				for (Iterator<IPlayer> iterator = friendsSet.iterator(); iterator.hasNext();) 
				{
					IPlayer associatedPlayer = iterator.next();
					RummyGameAssociatedPlayersEntity rummyGameAssociatedPlayersEntity =
							new RummyGameAssociatedPlayersEntity(gameID.longValue(), associatedPlayer.getEmailId());
					sessionFactory.getCurrentSession().save(rummyGameAssociatedPlayersEntity);
				}
		    }
		});
		return rummyGameEntity; 
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<RummyGameAssociatedPlayersEntity> getAssociatedPlayersListForGame(long gameId) {
		List<RummyGameAssociatedPlayersEntity> associatedPlayerEntityList = null;
		
		//Simple enough query... lets try Hibernate Criteria approach
		final Session session = sessionFactory.getCurrentSession();
		final Criteria criteria = session.createCriteria(RummyGameAssociatedPlayersEntity.class);
		criteria.add(Restrictions.eq("gameId", gameId));
		
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		
		associatedPlayerEntityList = tx.execute(new TransactionCallback<List<RummyGameAssociatedPlayersEntity>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<RummyGameAssociatedPlayersEntity> doInTransaction(TransactionStatus arg0) {
				return criteria.list();
			}
		});
		return associatedPlayerEntityList;
	}
	
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public List<Player> getPlayerNamesForPlayerIDs(List<String> playerIds) {
		final List<Player> playersWithNamesPopulated = new ArrayList<Player>();
		
		final Session session = sessionFactory.getCurrentSession();
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		
		final Query query = session.createQuery(HQL_GET_PLAYER_NAMES_FROM_IDS).setParameterList("playerIds", playerIds);
		
		tx.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				List<UserRegistryEntity> usersList = query.list();
				for ( UserRegistryEntity entity : usersList ) {
					Player player = new Player();
					player.setEmailId(entity.getUserId());
					player.setName(entity.getUserName());
					playersWithNamesPopulated.add(player);
				}
			}
		});
		return playersWithNamesPopulated;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public String getMailManagerPassword(final String mailManagerUserId) {
		final Session session = sessionFactory.getCurrentSession();
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		
		MailManagerEntity mailManagerEntity = tx.execute(new TransactionCallback<MailManagerEntity>() {
			@Override
			public MailManagerEntity doInTransaction(TransactionStatus arg0) {
				return (MailManagerEntity)session.get(MailManagerEntity.class, mailManagerUserId);
			}
		});
		
		String password = null;
		if ( mailManagerEntity != null ) {
			password = mailManagerEntity.getPassword();
		}
		return password;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public RummyGameEntity getGameById(final long gameId) {
		final Session session = sessionFactory.getCurrentSession();
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		
		RummyGameEntity gamesEntity = tx.execute(new TransactionCallback<RummyGameEntity>() {
			@Override
			public RummyGameEntity doInTransaction(TransactionStatus arg0) {
				return (RummyGameEntity)session.get(RummyGameEntity.class, gameId);
			}
		});
		
		return gamesEntity;
	}

	/* (non-Javadoc)
	 * @see com.webi.games.rummy.dao.IRummyGameDAO#getAllOpenGamesStartedByPlayer(com.webi.games.rummy.game.IPlayer)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public List<RummyGameEntity> getAllOpenGamesStartedByPlayer( IPlayer player) {
		
		Session session = sessionFactory.getCurrentSession();
		String originatorPlayerId = player.getEmailId();
		final Query query = session.createQuery(HQL_OPEN_GAMES_FOR_PLAYER);
		query.setString("originatorPlayerId",originatorPlayerId);
		
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		
		List<RummyGameEntity> openGamesEntityList = tx.execute(new TransactionCallback<List<RummyGameEntity>>() {
			@Override
			public List<RummyGameEntity> doInTransaction(TransactionStatus arg0) {
				return query.list();
			}
		});
		return openGamesEntityList;
	}
	
	/* (non-Javadoc)
	 * @see com.webi.games.rummy.dao.IRummyGameDAO#getAllOpenGamesInvitedForPlayer(com.webi.games.rummy.game.IPlayer)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<IRummyGame> getAllOpenGamesInvitedForPlayer(final IPlayer player) {
		final Session session = sessionFactory.getCurrentSession();
		final String playerId = player.getEmailId();
		final Query query = session.createQuery(HQL_OPEN_GAMES_INVITED_FOR_PLAYER);
		query.setString("playerId",playerId);
		//query.setString("gameAcceptStatus", GameAcceptStatus.NO_RESPONSE.toString());
		
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		
		final List<IRummyGame> openGamesList  = new ArrayList<IRummyGame>();
		tx.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				
				@SuppressWarnings("unchecked")
				List<Object[]> associatedPlayersEntityList = query.list();
				for (Iterator<Object[]> iterator = associatedPlayersEntityList.iterator(); iterator.hasNext();) {
					Object [] resultPair = iterator.next();
					RummyGameBO rummyGameBO =new RummyGameBO();
					rummyGameBO.populateFromEntity((RummyGameEntity)resultPair[0]);
					//Originator Player
					Player originatorPlayer = new Player();
					
					UserRegistryEntity userRegistryEntity  =  ((UserRegistryEntity)resultPair[2]);
					originatorPlayer.setEmailId(userRegistryEntity.getUserId());
					originatorPlayer.setName(userRegistryEntity.getUserName());
					rummyGameBO.setOriginatorPlayer(originatorPlayer);
					
					rummyGameBO.setAssociatedPlayerId(playerId);
					
					rummyGameBO.setGameAcceptanceStatus(((RummyGameAssociatedPlayersEntity)resultPair[1]).getGameAcceptStatus());
					openGamesList.add(rummyGameBO);
				}
			}
		});
		return openGamesList;
	}

	public List<RummyGameEntity> getGamesHistoryForPlayer( IPlayer player) {
		
		List<IRummyGame> openGamesList = null;
		Session session = sessionFactory.getCurrentSession();
		String originatorPlayerId = player.getEmailId();
		final Query query = session.createQuery(HQL_GAMES_HISTORY_FOR_PLAYER );
		query.setString("originatorPlayerId",originatorPlayerId);
		
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		
		List<RummyGameEntity> openGamesEntityList = tx.execute(new TransactionCallback<List<RummyGameEntity>>() {
			@Override
			public List<RummyGameEntity> doInTransaction(TransactionStatus arg0) {
				return query.list();
			}
		});
//		if ( openGamesEntityList != null && openGamesEntityList.isEmpty() == false ) {
//			openGamesList = new ArrayList<IRummyGame>();
//			
//			for (Iterator<RummyGameEntity> iterator = openGamesEntityList.iterator(); iterator
//					.hasNext();) {
//				RummyGameEntity rummyGameEntity = (RummyGameEntity) iterator.next();
//				openGamesList.add(new RummyGameBO().populateFromEntity(rummyGameEntity));
//			}
//		}
		return openGamesEntityList;
	}
	
	@Override
	@Transactional (propagation=Propagation.REQUIRED)
	public boolean deleteGame(IPlayer player, RummyGameEntity rummyGame) {
		boolean deleteResult = false;
		final RummyGameEntity rummyGameEntity = new RummyGameEntity();
		rummyGameEntity.setGameId(rummyGame.getGameId());
		
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		
		tx.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				
				Session session = sessionFactory.getCurrentSession();
				
				//delete the main game entry
				session.delete(rummyGameEntity);
				
				//TODO: delete the associated players table
				
			}
		});
		
		return deleteResult;
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public RummyGameEntity getNotStartedOpenGame(IPlayer player) {
		RummyGameEntity rummyGame = null;
		String originatorPlayerId = player.getEmailId();
		Session session = sessionFactory.getCurrentSession();
		final Query query = session.createQuery(HQL_NOT_STARTED_GAME_FOR_PLAYER);
		query.setString("originatorPlayerId",originatorPlayerId);
		
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		
		List<RummyGameEntity> openGames = tx.execute(new TransactionCallback<List<RummyGameEntity>>() {
			@Override
			public List<RummyGameEntity> doInTransaction(TransactionStatus arg0) {
				return query.list();
			}
		});
		
		if ( openGames != null && openGames.isEmpty() == false ) 
		{
			rummyGame = openGames.get(0);
		}
		
		return rummyGame;
	}
	
	@Deprecated
	@Override
	public RummyGameHandPositionEntity getHandPositionEntity(final long gameId) {
		final Session session = sessionFactory.getCurrentSession();
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(datasource);
		TransactionTemplate tx = new TransactionTemplate(txManager);
		
		return tx.execute(new TransactionCallback<RummyGameHandPositionEntity>() {
			@Override
			public RummyGameHandPositionEntity doInTransaction(TransactionStatus arg0) {
				return (RummyGameHandPositionEntity)session.get(RummyGameHandPositionEntity.class, gameId);
			}
		});
	}
	
	
	/**
	 * Helper method to instantiate a new game entity object
	 * 
	 * @param gameName
	 * @param player
	 * @return
	 */
	private RummyGameEntity createNewRummyEntityObject(String gameName, IPlayer player) 
	{
		RummyGameEntity rummyGameEntity = new RummyGameEntity();
		Date currentTime = new Date(System.currentTimeMillis());
		rummyGameEntity.setCreationTime(currentTime);
		rummyGameEntity.setLastUpdatedTime(currentTime);
		rummyGameEntity.setOriginatorPlayerID(player.getEmailId());
		rummyGameEntity.setGameName(gameName);
		rummyGameEntity.setActive(false);
		
		return rummyGameEntity;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	
}