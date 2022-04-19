package com.gxdxx.shop.repository;

import com.gxdxx.shop.constant.ItemSellStatus;
import com.gxdxx.shop.dto.BoardSearchDto;
import com.gxdxx.shop.dto.ItemSearchDto;
import com.gxdxx.shop.dto.MainItemDto;
import com.gxdxx.shop.dto.QMainItemDto;
import com.gxdxx.shop.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if (StringUtils.equals("boardTitle", searchBy)) {
            return QBoard.board.boardTitle.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QBoard.board.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Board> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable) {
        List<Board> content = queryFactory.selectFrom(QBoard.board)
                .where(searchByLike(boardSearchDto.getSearchBy(),
                                boardSearchDto.getSearchQuery()))
                .orderBy(QBoard.board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    private BooleanExpression boardNameLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QBoard.board.boardTitle.like("%" + searchQuery + "%");
    }

}
