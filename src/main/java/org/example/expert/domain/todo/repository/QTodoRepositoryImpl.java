package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.QTodoSearch;
import org.example.expert.domain.todo.dto.response.TodoSearch;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class QTodoRepositoryImpl implements QTodoRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Todo findByIdWithUser(Long todoId) {
        return queryFactory.selectFrom(todo)
                .leftJoin(todo.user, user)
                .fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchFirst();
    }

    @Override
    public Page<TodoSearch> findByTitleWithModifiedAtWithNicknameOrderByModifiedAtDesc(String title, LocalDateTime startDate, LocalDateTime endDate, String nickname, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(todo.title.contains(title));
        builder.and(todo.modifiedAt.goe(startDate));
        builder.and(todo.modifiedAt.loe(endDate));
        builder.and(todo.user.nickname.contains(nickname));

        List<TodoSearch> todoSearch = queryFactory.select(
                        new QTodoSearch(
                                todo.title,
                                manager.countDistinct(),
                                comment.count()
                        )
                ).from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .leftJoin(todo.user, user)
                .where(builder)
                .groupBy(todo.title, todo.modifiedAt)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(todo.modifiedAt.desc())
                .fetch();

        Long totalTodos = queryFactory.select(todo.count())
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .leftJoin(todo.user, user)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(todoSearch, pageable, totalTodos);
    }
}
