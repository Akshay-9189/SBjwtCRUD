package com.csi.repo;

import com.csi.model.UserDetailsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserDetailsRepo extends JpaRepository<UserDetailsInfo, Integer> {

    UserDetailsInfo findByUserName(String userName);

    UserDetailsInfo findByUserId(int userId);
}
