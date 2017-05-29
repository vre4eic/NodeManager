/**
 * 
 */
package eu.vre4eic.evre.nodeservice.usermanager.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStoreException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Hashtable;
import java.util.List;










import javax.net.ssl.HttpsURLConnection;










import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import eu.vre4eic.evre.core.Common;
import eu.vre4eic.evre.core.EvreEvent;
import eu.vre4eic.evre.core.EvreQuery;
import eu.vre4eic.evre.core.UserCredentials;
import eu.vre4eic.evre.core.UserProfile;
import eu.vre4eic.evre.core.Common.ResponseStatus;
import eu.vre4eic.evre.core.impl.EVREUserProfile;
import eu.vre4eic.evre.core.messages.AuthenticationMessage;
import eu.vre4eic.evre.core.messages.Message;
import eu.vre4eic.evre.core.messages.MultiFactorMessage;
import eu.vre4eic.evre.core.messages.impl.AuthenticationMessageImpl;
import eu.vre4eic.evre.core.messages.impl.MessageImpl;
import eu.vre4eic.evre.core.messages.impl.MultiFactorMessageImpl;
import eu.vre4eic.evre.nodeservice.Utils;
import eu.vre4eic.evre.core.comm.Publisher;
import eu.vre4eic.evre.core.comm.PublisherFactory;
import eu.vre4eic.evre.nodeservice.usermanager.UserManager;
import eu.vre4eic.evre.nodeservice.usermanager.dao.UserProfileRepository;


/**
 * @author cesare
 *
 */
@Configuration

public class UserManagerImpl implements UserManager {

	static int TOKEN_TIMEOUT = Integer.valueOf(Utils.getNodeServiceProperties().getProperty("TOKEN_TIMEOUT"));
	static int CODE_TIMEOUT = Integer.valueOf(Utils.getNodeServiceProperties().getProperty("CODE_TIMEOUT"));


	private Hashtable<String, AuthenticationMessage> pendingUsers = new Hashtable<String,AuthenticationMessage>();


	@Autowired
	private UserProfileRepository repository;
	/**
	 * 
	 */
	public UserManagerImpl() {
		super();
	}

	/**
	 * Create a user profile  
	 * @return Message 
	 */
	@Override
	public Message createUserProfile(EVREUserProfile profile) {

		if (repository.findByUserId(profile.getUserId())!=null)
			return( new MessageImpl("Operation not executed, User Id not unique", Common.ResponseStatus.FAILED));

		if (repository.save(profile)!=null)
		//	return (this.updateAAAI(profile.getUserId(), profile.getPassword(), "evre"));
			return new MessageImpl("Operation completed", Common.ResponseStatus.SUCCEED);


		return( new MessageImpl("Error, please contact server admin", Common.ResponseStatus.FAILED));
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#updateUserProfile(java.lang.String, eu.vre4eic.evre.core.UserProfile)
	 */
	@Override
	public Message updateUserProfile(String userId, EVREUserProfile profile) {
		if (repository.findByUserId(userId)==null)
			return( new MessageImpl("Operation not executed, User profile not found", Common.ResponseStatus.FAILED));

		repository.save(profile);
		return( new MessageImpl("Operation completed", Common.ResponseStatus.SUCCEED));
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#removeUserProfile(java.lang.String)
	 */
	@Override
	public Message removeUserProfile(String userId) {
		EVREUserProfile profile= repository.findByUserId(userId);
		if (profile==null)
			return( new MessageImpl("Operation not executed, User profile not found", Common.ResponseStatus.FAILED));


		repository.delete(profile);
		return( new MessageImpl("Operation completed", Common.ResponseStatus.SUCCEED));


	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#getUserProfile(java.lang.String)
	 */
	@Override
	public EVREUserProfile getUserProfile(String userId) {

		EVREUserProfile profile= repository.findByUserId(userId);
		return profile;	



	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#getUserProfile(eu.vre4eic.evre.core.UserCredentials)
	 */
	@Override
	public EVREUserProfile getUserProfile(UserCredentials credentials) {
		EVREUserProfile profile= repository.findByUserId(credentials.getUserId());
		if (profile!=null && credentials.getPassword().equals(profile.getPassword())){
			profile.setPassword("");
			return profile;
		}
		return null;

	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#getUserProfile(eu.vre4eic.evre.core.EvreQuery)
	 */
	@Override
	public List<EVREUserProfile> getUserProfile(EvreQuery query) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#getUserProfile(eu.vre4eic.evre.core.EvreQuery)
	 */
	@Override
	public List<EVREUserProfile> getAllUserProfiles() {
		
			return repository.findAll();
		
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#subscribeEvent(java.lang.String, java.util.List)
	 */
	@Override
	public Message subscribeEvent(String idUser, List<EvreEvent> events) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#unSubscribeEvent(java.lang.String, java.util.List)
	 */
	@Override
	public Message unSubscribeEvent(String idUser, List<EvreEvent> events) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#checkEvent(java.lang.String, java.lang.String)
	 */
	@Override
	public Message checkEvent(String userId, String eventId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#checkEvent(java.lang.String)
	 */
	@Override
	public List<Message> checkEvent(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#getSubscribedEvents(java.lang.String)
	 */
	@Override
	public List<EvreEvent> getSubscribedEvents(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#login(eu.vre4eic.evre.core.UserCredentials)
	 */
	@Override
	public AuthenticationMessage login(UserCredentials credentials) {
		AuthenticationMessage ame = this.getLoginMessage(credentials.getUserId(), credentials.getPassword());
		if (ame.getStatus() == Common.ResponseStatus.SUCCEED) {
			Publisher<AuthenticationMessage> p =  PublisherFactory.getAuthenticationPublisher();
			p.publish(ame);
		}
		return ame;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#login(java.lang.String, java.lang.String)
	 */
	@Override
	public AuthenticationMessage loginMFA(String userId, String password) {

		// check credentials
		AuthenticationMessage ame= this.getLoginMessage(userId, password);

		if (ame.getStatus() == Common.ResponseStatus.SUCCEED){
			UserProfile profile= repository.findByUserId(userId);

			// generate code
			String code = Utils.generateCode();

			// set code expiration time
			LocalDateTime codeTimeLimit = LocalDateTime.now().plusMinutes(CODE_TIMEOUT);
			ame.setTimeLimit(codeTimeLimit);

			// save code
			String key = ame.getToken()+"#"+code;
			synchronized (pendingUsers) {
				pendingUsers.put(key, ame);
			}

			// publish
			Publisher<MultiFactorMessage> p =  PublisherFactory.getMFAPublisher();
			MultiFactorMessage mfam;

			mfam = new MultiFactorMessageImpl("", ResponseStatus.IN_PROGRESS);

			mfam.setAuthId(profile.getAuthId());
			mfam.setUserId(profile.getUserId());
			mfam.setCode(code);
			p.publish(mfam);

		}

		return ame;
	}

	@Override
	public AuthenticationMessage loginMFACode(String token, String code) {

		String key = token + "#" + code;
		AuthenticationMessage ame;
		synchronized (pendingUsers) {
			ame = pendingUsers.remove(key);			
		}

		if (ame == null){
			AuthenticationMessage error =  new AuthenticationMessageImpl();
			error.setStatus(ResponseStatus.FAILED)
			.setMessage("LoginMFAcode failed");
			return error;
		}
		if (isCodeExpired(ame)){
			AuthenticationMessage error =  new AuthenticationMessageImpl();
			error.setStatus(ResponseStatus.FAILED)
			.setMessage("Code expired, please login again !");
			return error;
		}

		Publisher<AuthenticationMessage> p =  PublisherFactory.getAuthenticationPublisher();
		LocalDateTime timeLimit = LocalDateTime.now().plusMinutes(TOKEN_TIMEOUT);
		ame.setTimeLimit(timeLimit);
		p.publish(ame);
		return ame;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#logout(java.lang.String)
	 *change this implementation
	 */
	@Override
	public AuthenticationMessage logout(String token) {
		Publisher<AuthenticationMessage> p =  PublisherFactory.getAuthenticationPublisher();

		LocalDateTime timeLimit;
		AuthenticationMessage ame;

		timeLimit = LocalDateTime.MIN;
		ame = new AuthenticationMessageImpl(Common.ResponseStatus.SUCCEED, "Operation completed",
				token, Common.UserRole.ADMIN,timeLimit);
		ame.setTimeZone(ZoneId.systemDefault().getId());
		p.publish(ame);
		return ame;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#registerAuthenticator(eu.vre4eic.evre.core.UserCredentials, java.lang.String)
	 */
	@Override
	public Message registerAuthenticator(UserCredentials credentials,
			String authId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#removeAuthenticator(eu.vre4eic.evre.core.UserCredentials, java.lang.String)
	 */
	@Override
	public Message removeAuthenticator(UserCredentials credentials,
			String authId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#authenticate(eu.vre4eic.evre.core.UserCredentials, java.lang.String)
	 */
	@Override
	public Message authenticate(UserCredentials credentials, String authId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.vre4eic.evre.nodeservice.usermanager.UserManager#synchCredentials(java.lang.String, java.util.List)
	 */
	@Override
	public Message synchCredentials(String authId,
			List<UserCredentials> credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message passwordRecovery(String email) {
		UserProfile profile= repository.findByEmail(email);
		if (profile !=null){
			//TO BE IMPLEMENTED: send an email message to recover password
			return (new MessageImpl("An email has been sent to the specified address", Common.ResponseStatus.SUCCEED));
		}

		return (new MessageImpl("No user found with the specified email", Common.ResponseStatus.EMPTY_RESULT));

	}
	//private methods

	private AuthenticationMessage getLoginMessage(String login, String password){

		AuthenticationMessage ame;

		ame = new AuthenticationMessageImpl(Common.ResponseStatus.FAILED, "Invalid credentials",
				"", null,LocalDateTime.MIN);
		ame.setTimeZone(ZoneId.systemDefault().getId());
		ame.setTimeLimit(LocalDateTime.MIN);
		UserProfile profile= repository.findByUserId(login);

		if (profile==null || !password.equals(profile.getPassword()))
			return ame;

		LocalDateTime timeLimit = LocalDateTime.now().plusMinutes(TOKEN_TIMEOUT);
		ame = new AuthenticationMessageImpl(Common.ResponseStatus.SUCCEED, "Operation completed",
				profile.getPassword(), profile.getRole(),timeLimit);

		ame.setTimeZone(ZoneId.systemDefault().getId())
		.setRenewable(String.valueOf(TOKEN_TIMEOUT))
		.setToken(Utils.generateToken());

		return ame;
	}


	private boolean isCodeExpired (AuthenticationMessage am) {
		ZoneId zone = ZoneId.of(am.getTimeZone());
		LocalDateTime now = LocalDateTime.now(zone);
		return now.isAfter(am.getTimeLimit());
	}


	private Message updateAAAI(String userId, String pass, String groupId){
		try {

			//need to set these in the config file
			String userPassword = "admin" + ":" + "LDAD5aKoC7";
			//in AAI: @Path("/group/{groupPath}/entity/{entityId}")
			String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());
			String credentialReq = URLEncoder.encode("Password requirement", "UTF-8") ;
			String postData = URLEncoder.encode("group/CWI", "UTF-8") ;
			
			// create new entity
			
			URL myUrl = new URL("https://v4e-lab.isti.cnr.it:2443/rest-admin/v1/entity/identity/userName/"+userId+"?credentialRequirement="+credentialReq);//add entity
			
			HttpsURLConnection conn = (HttpsURLConnection) myUrl.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Basic "+encoding );



			InputStream inputStream = conn.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
			bufferedReader.close();
			inputStream.close();
			conn.disconnect();
			
			//add password to entity
			//{"password":"newpass","answer":"Some answer","question":1}
			///entity/{entityId}/credential-adm/{credential}, @QueryParam("identityType"), PUT
			String credential=URLEncoder.encode("{\"password\":\""+pass+"\", \"answer\":\"Some answer\",\"question\":1}", "UTF-8") ;
			myUrl = new URL("https://v4e-lab.isti.cnr.it:2443/rest-admin/v1/entity/"+userId+"/credential-adm/"+credentialReq+"?identityType=userName");//password for the entity
			
			conn = (HttpsURLConnection) myUrl.openConnection();
			conn.setRequestProperty("Authorization", "Basic "+encoding );
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty( "Content-Type", "application/json" );
			OutputStreamWriter out = new OutputStreamWriter(
				    conn.getOutputStream());
			out.write(credential);
			out.close();
			//conn.setDoInput(true);
			
			inputStream = conn.getInputStream();
			 bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			 line = "";
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
			bufferedReader.close();
			inputStream.close();
			conn.disconnect();
			
			myUrl = new URL("https://v4e-lab.isti.cnr.it:2443/rest-admin/v1/group/"+groupId+"/entity/"+userId+"?identityType=userName");//add entity to group
			
			//add entity to group
			conn = (HttpsURLConnection) myUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Basic "+encoding );
			inputStream = conn.getInputStream();
			 bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			 line = "";
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
			bufferedReader.close();
			inputStream.close();
			conn.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return( new MessageImpl("Error, please contact the server admin", Common.ResponseStatus.FAILED));
		} 
		return( new MessageImpl("Operation completed", Common.ResponseStatus.SUCCEED));
	}


	static {
		HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> hostname.equals("v4e-lab.isti.cnr.it"));
	}
}
