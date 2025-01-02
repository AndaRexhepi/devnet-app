package org.example.devnet.newbiecorner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoadmapsController {

    @GetMapping("/newbiecorner")
    public String newbie_corner(Model model){
        return "newbiecorner/newbiecorner";
    }

    @GetMapping("/frontend_roadmap")
    public String getFrontEnd(Model model){
        return "newbiecorner/roadmaps/frontend_roadmap";
    }

    @GetMapping("/backend_roadmap")
    public String getBackEnd(Model model){
        return "newbiecorner/roadmaps/backend_roadmap";
    }

    @GetMapping("/gamedev_roadmap")
    public String getGameDev(Model model){
        return "newbiecorner/roadmaps/gamedev_roadmap";
    }

    @GetMapping("/mobiledev_roadmap")
    public String getMobileDev(Model model){
        return "newbiecorner/roadmaps/mobiledev_roadmap";
    }

}
