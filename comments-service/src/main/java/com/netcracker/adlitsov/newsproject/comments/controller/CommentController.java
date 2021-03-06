package com.netcracker.adlitsov.newsproject.comments.controller;

import com.netcracker.adlitsov.newsproject.comments.exception.ResourceNotFoundException;
import com.netcracker.adlitsov.newsproject.comments.model.Comment;
import com.netcracker.adlitsov.newsproject.comments.model.Vote;
import com.netcracker.adlitsov.newsproject.comments.repository.CommentsRepository;
import com.netcracker.adlitsov.newsproject.comments.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentsService commentsService;

    @GetMapping()
    public List<Comment> getAllComments() {
        return commentsService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable("id") Integer id) {
        return commentsService.getCommentById(id);
    }

    @PostMapping()
    public Comment createComment(@RequestBody Comment comment) {
        return commentsService.createComment(comment);
    }



    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable(value = "id") Integer commentId,
                                 @Valid @RequestBody Comment commentDetails) {
        return commentsService.updateComment(commentId, commentDetails);
    }

    @PutMapping("/{id}/hide")
    public Comment hideComment(@PathVariable(value = "id") Integer commentId) {
        return commentsService.hideComment(commentId);
    }

    @PutMapping("/{id}/show")
    public Comment showComment(@PathVariable(value = "id") Integer commentId) {
        return commentsService.showComment(commentId);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable(value = "id") Integer commentId) {
        commentsService.deleteComment(commentId);
    }

    @DeleteMapping("/article/{id}")
    public void deleteCommentsByArticleId(@PathVariable(value = "id") Integer articleId) {
        commentsService.deleteCommentByArticleId(articleId);
    }

    @GetMapping(params = "articleId")
    public List<Comment> getCommentsByArticleId(@RequestParam("articleId") Integer articleId) {
        return commentsService.getCommentsByArticleId(articleId);
    }

    @GetMapping(params = {"articleId", "root"})
    public List<Comment> getRootCommentsByArticleId(@RequestParam("articleId") Integer articleId) {
        return commentsService.getRootCommentsByArticleId(articleId);
    }

    @GetMapping(params = "authorId")
    public List<Comment> getCommentsByAuthorId(@RequestParam("authorId") Integer authorId) {
        return commentsService.getCommentsByAuthorId(authorId);
    }

    @GetMapping(params = {"articleId", "authors"})
    public List<Integer> getAuthorsByArticleId(@RequestParam("articleId") Integer articleId) {
        return this.commentsService.getAuthorsIdByArticleId(articleId);
    }

    @GetMapping(value = "/my-votes", params = "articleId")
    public Map<Integer, Vote> getMyVotes(@RequestParam("articleId") Integer articleId, Authentication auth) {
        return commentsService.getMyVotes(articleId, auth);
    }

    @PostMapping("/{id}/like")
    public int likeComment(@PathVariable("id") int id, Authentication auth) {
        return commentsService.voteComment(id, Vote.VoteType.LIKE, auth).getRating();
    }

    @PostMapping("/{id}/dislike")
    public int dislikeComment(@PathVariable("id") int id, Authentication auth) {
        return commentsService.voteComment(id, Vote.VoteType.DISLIKE, auth).getRating();
    }
}