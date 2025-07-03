package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.example.expert.domain.todo.entity.Todo;

public interface QTodoRepository {

    Todo findByIdWithUser(Long todoId);

}
