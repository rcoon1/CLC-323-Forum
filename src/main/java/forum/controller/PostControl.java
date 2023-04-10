package forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import forum.model.Post;
import forum.service.PostService;

@Controller
public class PostControl {
    private final PostService service;

    public PostControl(PostService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Post post) {
        service.save(post);
        return "redirect:/index";
    }

    @RequestMapping("/edit")
    public String edit(Model model,
                       @RequestParam(required = false) Integer id) {
        Post post = new Post();
        if (id != null) {
            post = service.get(id);
        }
        model.addAttribute("post", post);
        return "edit";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam int id) {
        service.delete(id);
        return "redirect:/index";
    }

}
