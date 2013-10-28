package com.ainq.sample.portlet.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.ainq.sample.portlet.models.SamplePortletModel;

@Controller
@RequestMapping("VIEW")
public class SamplePortletController {
	@ModelAttribute("sample")
	public SamplePortletModel getCommandObject() {

		return new SamplePortletModel();
	}

	@ResourceMapping("addUser")
	public void addUser(ResourceRequest request, ResourceResponse response) throws IOException {
		// serve resource here
		

		OutputStream outStream = response.getPortletOutputStream();
		StringBuffer buffer = new StringBuffer();

		SamplePortletModel sample = new SamplePortletModel();
		sample.setFirstName(request.getParameter("sample[firstName]"));

		sample.setLastName(request.getParameter("sample[lastName]"));

		sample.setUsername(request.getParameter("sample[username]"));
		
		System.out.println("First Name " + sample.getFirstName());

		String test = new JSONObject(sample).toString();
		buffer.append(test);
		
		System.out.println(buffer.toString());

		outStream.write(buffer.toString().getBytes());
	}

	@ActionMapping(params = "action=addUser")
	public void processUserAdd(ActionRequest arg0, ActionResponse arg1,
			@ModelAttribute(value = "sample") SamplePortletModel sample)
			throws Exception {
		// TODO Auto-generated method stub

		System.out.println("first name" + sample.getFirstName());

		arg1.setRenderParameter("action", "addUser");
	}

	@RenderMapping(params = "action=addUser")
	public ModelAndView renderUserAdd(RenderRequest arg0, RenderResponse arg1,
			@ModelAttribute(value = "sample") SamplePortletModel sample)
			throws Exception {

		System.out.println("user Added render!!");

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("sample");

		modelAndView.addObject("sample", sample);

		return modelAndView;
	}

	@RenderMapping()
	public ModelAndView handleRenderRequest(RenderRequest arg0,
			RenderResponse arg1) throws Exception {

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("sample");

		return modelAndView;
	}

}
