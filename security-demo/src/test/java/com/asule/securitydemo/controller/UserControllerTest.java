package com.asule.securitydemo.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @Before
    public void setup(){
        mockMvc=MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void query() throws Exception {
        String result=
                 mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username","leijun")
                .param("size","10")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }


    @Test
    public void getInfo() throws Exception {
        String result=mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("asule"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }


    @Test
    public void create() throws Exception {
//        String content="{\"username\":\"asule\",\"password\":\"\"}";

        String content="";

        String result=mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void update() throws Exception {
        String content="{\"id\":1,\"username\":\"asule\",\"password\":\"qwerty\"}";
        String result=mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }


    @Test
    public void testUpload() throws Exception {
        String result=mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
                .file(new MockMultipartFile("uploadFile","test.txt","multipart/form-data",
                        "opinion".getBytes())))
               . andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }


    @Test
    public void async() throws Exception {
        String result=mockMvc.perform(MockMvcRequestBuilders.get("/async/deferred"))
                . andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }




}


/*

    BasicErrorController





*/

