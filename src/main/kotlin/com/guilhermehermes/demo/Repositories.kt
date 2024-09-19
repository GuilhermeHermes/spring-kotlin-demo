package com.guilhermehermes.demo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ArticleRepositories: JpaRepository<Article, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Article>
    fun findBySlug(slug: String): Article?
}