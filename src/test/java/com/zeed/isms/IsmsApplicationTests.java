package com.zeed.isms;

import com.zeed.isms.lib.enums.PresentClass;
import com.zeed.isms.lib.models.IsmsUser;
import com.zeed.isms.lib.models.RegistrationDetails;
import com.zeed.isms.lib.repository.IsmsUserRepository;
import com.zeed.isms.lib.repository.RegistrationDetailsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.zeed.isms.lib.enums.ClassLevel.JSS;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IsmsApplicationTests {

	@Autowired
	public IsmsUserRepository ismsUserRepository;

	@Autowired
	public RegistrationDetailsRepository registrationDetailsRepository;

	@Test
	public void contextLoads() {

		List<IsmsUser> ismsUsers = ismsUserRepository.findAll();
		List<IsmsUser> ismsUsers1 = ismsUserRepository.findAllByManagedUserId(Long.valueOf(1));
		long a = 1;
		List<IsmsUser> ismsUsers2 = ismsUserRepository.findAllById(1L);
		List<IsmsUser> ismsUsers3 = ismsUserRepository.findAllByClassLevel(JSS);
		List<IsmsUser> ismsUsers4 = ismsUserRepository.findAllBypresentClass(PresentClass.JSS1);


		List<RegistrationDetails> registrationDetails = registrationDetailsRepository.findAll();

//		RegistrationDetails registrationDetails1 = registrationDetailsRepository.findOne(1L);
		List<RegistrationDetails> registrationDetails3 = registrationDetailsRepository.findAllById(1L);
		RegistrationDetails registrationDetails2 = registrationDetailsRepository.findByRegNo("");



		System.out.println("Done");

	}


}
