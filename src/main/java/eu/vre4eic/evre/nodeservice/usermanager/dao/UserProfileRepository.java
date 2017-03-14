package eu.vre4eic.evre.nodeservice.usermanager.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import eu.vre4eic.evre.core.Common.UserRole;
import eu.vre4eic.evre.core.UserProfile;
import eu.vre4eic.evre.core.impl.EVREUserProfile;


public interface UserProfileRepository extends MongoRepository <EVREUserProfile, String> {
	public EVREUserProfile findByUserId(String userId);
    public List<EVREUserProfile> findByRole(UserRole role);
    public List<EVREUserProfile> findByPassword(String passsword);

}
