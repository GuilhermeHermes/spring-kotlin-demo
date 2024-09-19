package com.guilhermehermes.demo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/v1/articles")
class ArticleController(val articleRepositories: ArticleRepositories) {



    @GetMapping
    fun getAllArticles(): MutableList<Article> = articleRepositories.findAll()

    @GetMapping("/{slug}")
    fun articles(@PathVariable slug: String) = articleRepositories.findBySlug(slug)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found")
    //deveria ser ORELSETHROW
    @PostMapping
    fun newArticle (@RequestBody article: Article) {
        articleRepositories.save(article)
    }


    @PutMapping("/{slug}")
    fun updateArticle(@PathVariable slug: String, @RequestBody updatedArticle: Article): Article {
        var existingArticle = articleRepositories.findBySlug(slug)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found")

        val newArticle = existingArticle.copy(
            title = updatedArticle.title,
            content = updatedArticle.content ?: existingArticle.content,
            createdAt = existingArticle.createdAt,
            slug = updatedArticle.title.toSlug()
        )

        existingArticle = newArticle
        return articleRepositories.save(existingArticle)
    }

    @DeleteMapping("/{slug}")
    fun deleteArticle(@PathVariable slug: String) {
        val article = articleRepositories.findBySlug(slug) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found")
        articleRepositories.delete(article)
    }
}