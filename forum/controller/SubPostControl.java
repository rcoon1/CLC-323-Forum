package forum.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import forum.model.Post;
import forum.model.SubPost;
import forum.service.PostService;
import forum.service.SubPostService;

import java.util.List;

@Controller
public class SubPostControl {
    private final PostService pServ;
    private final SubPostService subServ;

    public SubPostControl(PostService pServ, SubPostService subServ) {
        this.pServ = pServ;
        this.subServ = subServ;
    }

    @GetMapping("/post")
    public String post(Model model, @RequestParam int postId) {
        Post post = pServ.get(postId);
        model.addAttribute("post", post);
        List<SubPost> subs = subServ.getAllByPostId(postId);
        model.addAttribute("subs", subs);
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "post";
    }

    @PostMapping("/subpost/save")
    public String saveSubpost(@ModelAttribute SubPost sub, @RequestParam int postId) {
        sub.setPost(pServ.get(postId));
        subServ.save(sub);
        return "redirect:/post?postId=" + postId;
    }

    @RequestMapping("/subpost/update")
    public String updateSubpost(Model model, @RequestParam int subId,
                                @RequestParam int postId) {
        SubPost sub = subServ.get(subId);
        model.addAttribute("sub", sub);
        model.addAttribute("postId", postId);
        return "subpost/edit";
    }

    @RequestMapping("/subpost/delete")
    public String deleteSubpost(Model model, @RequestParam int subId,
                                @RequestParam int postId) {
        subServ.delete(subId);
        return "redirect:/post?postId=" + postId;
    }
}
