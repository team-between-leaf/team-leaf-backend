package com.team.leaf.shopping.history.repository;

import com.team.leaf.shopping.history.entity.History;
import com.team.leaf.user.account.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    long deleteByHistoryIdAndAccountDetail(long historyId, AccountDetail accountDetail);

    List<History> findAllByAccountDetailOrderByHistoryIdDesc(AccountDetail accountDetail);

    List<History> findAllByAccountDetail(AccountDetail accountDetail);

    long deleteAllByAccountDetail(AccountDetail accountDetail);

}
