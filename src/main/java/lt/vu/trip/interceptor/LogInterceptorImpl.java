package lt.vu.trip.interceptor;

import lt.vu.trip.entity.user.User;
import lt.vu.trip.service.user.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@LogInterceptor
@Interceptor
public class LogInterceptorImpl implements Serializable {

	@Autowired
	private Logger logger;

	@Autowired
	private UserService userService;

	@AroundInvoke
	private Object doLog(InvocationContext ic) {
		Object obj = null;
		try {
			User user = userService.getCurrentUser();

			if (user != null) {
				logger.info("User: " + user.getUsername() + " with rights: " + Arrays.toString(user.getRoles().toArray())
						+ " has accessed class: " + ic.getTarget().toString() + " method: " + ic.getMethod().getName()
						+ " at time: " + getTime());
			}

			obj = ic.proceed();
		} catch (Exception ex) {
			logger.error("Error while logging");
		}
		return obj;
	}

	private static String getTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
	}
}
