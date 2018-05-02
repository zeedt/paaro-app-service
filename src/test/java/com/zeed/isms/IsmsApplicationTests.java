package com.zeed.isms;

import com.zeed.generic.RestApiClient;
import com.zeed.isms.lib.enums.PresentClass;
import com.zeed.isms.lib.models.Course;
import com.zeed.isms.lib.models.IsmsUser;
import com.zeed.isms.lib.models.RegistrationDetails;
import com.zeed.isms.lib.models.Teacher;
import com.zeed.isms.lib.repository.CourseRepository;
import com.zeed.isms.lib.repository.IsmsUserRepository;
import com.zeed.isms.lib.repository.RegistrationDetailsRepository;
import com.zeed.isms.lib.repository.TeacherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;

import static com.zeed.isms.lib.enums.ClassLevel.JSS;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IsmsApplicationTests {

	@Autowired
	public IsmsUserRepository ismsUserRepository;

	@Autowired
	public RegistrationDetailsRepository registrationDetailsRepository;

	@Autowired
	private RestApiClient restApiClient;

	@Autowired
	public CourseRepository courseRepository;

	@Autowired
	public TeacherRepository teacherRepository;

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

	@Test
	public void testTeacherAndCourseRepo(){
		List<Teacher> teacher = teacherRepository.findAllById(1L);
		List<Course> courses = courseRepository.findAllById(1L);

		System.out.println("Done");

	}

	@Test
	public void testSomething() throws Exception {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer d0cc11a7-63a7-4b60-ba43-f806c5650c94");
		MultiValueMap<String, Object> request = new LinkedMultiValueMap();
		String url = "http://127.0.0.1:7071/course/fetchAll";
		ResponseEntity<Object> object = restApiClient.apiGetAndGetResponseEntity(url, Object.class,null,headers);
		System.out.println("Done");
	}

}
