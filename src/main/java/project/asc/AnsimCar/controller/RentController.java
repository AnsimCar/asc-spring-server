package project.asc.AnsimCar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;
import project.asc.AnsimCar.dto.rent.request.RentSearchRequest;
import project.asc.AnsimCar.dto.rent.response.RentItemDetailResponse;
import project.asc.AnsimCar.service.RentService;

@Controller
@RequestMapping("/rent")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @ModelAttribute("carCategories")
    public CarCategory[] carCategories() {
        return CarCategory.values();
    }

    @ModelAttribute("fuels")
    public Fuel[] fuels() {
        return Fuel.values();
    }

    @GetMapping("/list")
    public String list(@ModelAttribute("rentSearchRequest") RentSearchRequest request, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        Page<RentItemDetailResponse> rentItemDetailResponses = rentService.findByAvailable(pageable);

        return getPagingList(pageable, model, rentItemDetailResponses);
    }

    @PostMapping("/list")
    public String SearchList(@ModelAttribute("rentSearchRequest") RentSearchRequest request,
                             @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model) {

        Page<RentItemDetailResponse> rentItemDetailResponses = rentService.findAllComplex(request, pageable);

        return getPagingList(pageable, model, rentItemDetailResponses);
    }

    private String getPagingList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model, Page<RentItemDetailResponse> rentItemDetailResponses) {
        int nowPage = rentItemDetailResponses.getPageable().getPageNumber() + 1;
        model.addAttribute("list", rentItemDetailResponses);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", Math.max(nowPage - 4, 1));
        model.addAttribute("endPage", Math.min(nowPage + 5, rentItemDetailResponses.getTotalPages()));

        return "rent/list";
    }

}
