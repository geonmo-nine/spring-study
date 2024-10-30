package hello.hellospring.controller

import hello.hellospring.domain.Member
import hello.hellospring.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/members/new")
    fun memberNew() = "member/createMemberForm"

    @PostMapping("/members/new")
    fun createMember(memberForm: MemberForm): String {
        val member = Member(name = memberForm.name)
        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun members(model: Model): String {
        val members = memberService.findMembers()
        model.addAttribute("members", members)
        return "member/memberList"
    }
}