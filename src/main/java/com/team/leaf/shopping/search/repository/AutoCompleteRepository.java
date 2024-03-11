package com.team.leaf.shopping.search.repository;

import com.team.leaf.shopping.search.entity.AutoComplete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutoCompleteRepository extends JpaRepository<AutoComplete, Long> {

    Optional<AutoComplete> findAutoCompleteByWord(String word);

}
