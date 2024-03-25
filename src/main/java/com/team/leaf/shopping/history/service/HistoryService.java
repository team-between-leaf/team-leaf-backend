package com.team.leaf.shopping.history.service;

import com.team.leaf.shopping.history.dto.HistoryRequest;
import com.team.leaf.shopping.history.dto.HistoryResponse;
import com.team.leaf.shopping.history.entity.History;
import com.team.leaf.shopping.history.repository.HistoryRepository;
import com.team.leaf.user.account.entity.AccountDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Transactional
    public void deleteHistory(long historyId, AccountDetail accountDetail) {
        historyRepository.deleteByHistoryIdAndAccountDetail(historyId, accountDetail);
    }

    @Transactional
    public void addHistory(HistoryRequest request, AccountDetail accountDetail) {
        History history = History.createHistory(request.getContent(), accountDetail);

        List<History> historyList = historyRepository.findAllByAccountDetail(accountDetail);
        for (int i = 0; i < historyList.size() - 9; i++) {
            historyRepository.delete(historyList.get(i));
        }

        historyRepository.save(history);
    }

    public List<History> getAllHistory(AccountDetail accountDetail) {
        return historyRepository.findAllByAccountDetailOrderByHistoryIdDesc(accountDetail);
    }

    @Transactional
    public void deleteAllHistory(AccountDetail accountDetail) {
        historyRepository.deleteAllByAccountDetail(accountDetail);
    }

}
