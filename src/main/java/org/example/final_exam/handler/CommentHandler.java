package org.example.final_exam.handler;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.example.final_exam.model.Article;
import org.example.final_exam.model.Comment;
import org.example.final_exam.model.User;
import org.example.final_exam.respository.ArticleRepository;
import org.example.final_exam.respository.CommentRepository;
import org.example.final_exam.respository.UserRepository;
import org.example.final_exam.security.Secured;
import java.util.HashMap;
import java.util.Map;
@Path("/comments")
public class CommentHandler {
    @Inject
    private CommentRepository commentRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ArticleRepository articleRepository;

    @POST
    @Path("/")
    @Secured({"user", "admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(Comment comment, @Context SecurityContext securityContext) {
        User user = userRepository.findByID(Integer.valueOf(securityContext.getUserPrincipal().getName()));  // 从 SecurityContext 中获得当前登录的用户
        comment.setUser(user);
        Article article = articleRepository.findByID(comment.getArticleId());
        comment.setArticle(article);
        comment.setCreatedAt(System.currentTimeMillis() / 1000);
        commentRepository.create(comment);
        Map<String, Object> res = new HashMap<>();
        res.put("code", Response.Status.OK);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @DELETE
    @Path("/{id}")
    @Secured({"user", "admin"})  // 限制只有真正的 user 和 admin 才可访问这个接口，只有经过认证的用户才能发评论，不然乱套了
    @Consumes(MediaType.APPLICATION_JSON)  // 指定请求体的媒体类型为 JSON
    @Produces(MediaType.APPLICATION_JSON)  // 接收 JSON 格式的数据
    public Response deleteComment(@PathParam("id")Integer id/*PathParam是将提取@Path里面的id参数，并转换成Integer类型*/, @Context SecurityContext securityContext /* 从请求上下文中获得在 AuthenticationFilter 中的 SecurityContext */) {
        commentRepository.deleteById(id);
        Map<String, Object> res = new HashMap<>();
        res.put("code", Response.Status.OK);
        return Response.status(Response.Status.OK).entity(res).build();
    }
}
