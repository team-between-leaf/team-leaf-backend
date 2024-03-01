package com.team.leaf.alert.repository;

import com.team.leaf.alert.entity.Alert;
import com.team.leaf.user.account.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> , CustomAlertRepository {

}
