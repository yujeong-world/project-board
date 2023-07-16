package com.boardProject.fastcampusprojectboard.repository;

import com.boardProject.fastcampusprojectboard.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
