package com.iri.training.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("unused")
@Controller
public class ViewController {

	@RequestMapping(value = "")
	public String index() {
		return "app";
	}

	@RequestMapping(value = "*")
	public String notFound() {
		return "app";
	}

	@RequestMapping(value = "/error")
	public String errorPage() { return "app"; }

	@RequestMapping(value = "/auth/**")
	public String authPage() { return "app"; }

	@RequestMapping(value = "/charts/**")
	public String chartsPage() { return "app"; }

	@RequestMapping(value = "/user/**")
	public String userInfo() { return "app"; }

	@RequestMapping(value = "/register")
	public String userRegistration() { return "app"; }
}
