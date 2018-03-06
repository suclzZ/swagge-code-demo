package com.swagger.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.Placement;
import org.asciidoctor.SafeMode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import com.swagger.Application;

import io.github.swagger2markup.Swagger2MarkupConverter;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class Swagger2MarkupCodeTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
    public void convertSwaggerToAsciiDoc() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String swaggerJson = response.getContentAsString();
        Swagger2MarkupConverter.from(swaggerJson).build().toFolder(Paths.get(System.getProperty("io.springfox.staticdocs.snippetsOutputDir")));
        
        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        Attributes attributes = new Attributes();
        attributes.setCopyCss(true);
        attributes.setLinkCss(false);
        attributes.setSectNumLevels(3);
        attributes.setAnchors(true);
        attributes.setSectionNumbers(true);
        attributes.setHardbreaks(true);
        attributes.setTableOfContents(Placement.LEFT);
        attributes.setAttribute("generated", "generated");
        OptionsBuilder optionsBuilder = OptionsBuilder.options()
                .backend("html5")
                .docType("book")
                .eruby("")
                .inPlace(true)
                .safe(SafeMode.UNSAFE)
                .attributes(attributes);
        String asciiInputFile = System.getProperty("io.springfox.staticdocs.snippetsOutputDir")+"/../index.adoc";//先定义该文件
        initIndex(asciiInputFile);
        asciidoctor.convertFile(new File(asciiInputFile), optionsBuilder.get());
    }
	
	public static void initIndex(String path) {
		File index = new File(path);//index.adoc
		try {
			FileUtils.copyFile(ResourceUtils.getFile("classpath:index.adoc"), index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
