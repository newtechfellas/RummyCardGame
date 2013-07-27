package com.webi.ent.registration;

import java.util.List;

import com.webi.ent.common.vo.RegistrationVO;

public interface IUserRegistrationService {

	void resisterUser(RegistrationVO registrationVO) throws DuplicateUserRegistrationException;
	void unregisterUser(RegistrationVO registrationVO);
	List<RegistrationVO> showRegisterUsers();

}
