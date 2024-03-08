package com.team.leaf.shopping.chat.repository;

import com.team.leaf.shopping.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
