package com.tangyi.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangyi.constant.Role;
import com.tangyi.domain.Paper;
import com.tangyi.service.PaperService;
import com.tangyi.utils.IdGen;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tangyi on 2017/3/20.
 */

@RestController
@RequestMapping(value = "/v1/papers")
public class PaperController {

    private static Logger logger = LoggerFactory.getLogger(PaperController.class);

    //上传文件的存储路径
    public static final String localFilepath = "G:/angularjs/exam/fileServer/app/img/paper/";

    //文件服务器地址
    public static final String fileServer = "http://localhost:8765/static/img/paper/";

    @Autowired
    PaperService paperService;
    @ApiOperation(value = "获取试卷分页列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<Paper> getPaperListByPage(@RequestParam(required = false) Integer pageIndex,
                                              @RequestParam(required = false) Integer pageSize,
                                              @RequestParam(required = false) Integer limit,
                                              @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Paper> papers = paperService.getPaperList();
        PageInfo pageInfo = new PageInfo(papers);
        return pageInfo;
    }

    @ApiOperation(value = "根据类型获取试卷分页列表", notes = "")
    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public PageInfo<Paper> getPaperListByPage(@PathVariable String type,
                                              @RequestParam(required = false) Integer pageIndex,
                                              @RequestParam(required = false) Integer pageSize,
                                              @RequestParam(required = false) Integer limit,
                                              @RequestParam(required = false) Integer offset) {
        List<Paper> papers = null;
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        if(type.equals("official")) {
            papers = paperService.getOfficialPaperList();
        } else if(type.equals("simulate")) {
            papers = paperService.getSimulatePaperList();
        } else if(type.equals("practice")) {
            papers = paperService.getPracticePaperList();
        }
        PageInfo pageInfo = new PageInfo(papers);
        return pageInfo;
    }

    @ApiOperation(value = "获取最近已发布试卷分页列表", notes = "")
    @RequestMapping(value = "/published/{type}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public PageInfo<Paper> getPublishedPaperListByPage(@PathVariable String type,
                                                       @RequestParam(required = false) Integer pageIndex,
                                                       @RequestParam(required = false) Integer pageSize,
                                                       @RequestParam(required = false) Integer limit,
                                                       @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Paper> papers = type.equals("official") ?
                paperService.getPublishedOfficialPaperList() :
                paperService.getPublishedsimulatePaperList();
        PageInfo pageInfo = new PageInfo(papers);
        return pageInfo;
    }

    @ApiOperation(value = "创建试卷", notes = "创建试卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "科目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "paper", value = "试卷实体Paper", required = true, dataType = "Paper")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> postPaper(@PathVariable("id") String id, @RequestBody Paper paper) {
        paperService.savePaper(id, paper);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "获取试卷信息", notes = "根据试卷id获取试卷详细信息")
    @ApiImplicitParam(name = "id", value = "试卷ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "') or hasAuthority('" + Role.ROLE_STUDENT + "')")
    public Paper getPaper(@PathVariable String id) {
        return paperService.getPaperById(id);
    }

    @ApiOperation(value = "获取试卷信息", notes = "根据试卷name获取试卷详细信息")
    @ApiImplicitParam(name = "name", value = "试卷name", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<Paper> getPaperByName(@PathVariable String name) {
        //模糊查询
        return paperService.getPaperFuzzy(name);
    }

    @ApiOperation(value = "更新试卷信息", notes = "根据试卷id更新试卷信息")
    @ApiImplicitParam(name = "paper", value = "试卷实体", required = true, dataType = "Paper")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> putPaper(@RequestBody Paper paper) {
        paperService.updatePaper(paper);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "删除试卷", notes = "根据试卷id删除试卷")
    @ApiImplicitParam(name = "id", value = "试卷ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> deletePaper(@PathVariable String id) {
        paperService.deletePaper(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 上传图片
     * @param request
     * @return
     */
    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> uploadImg(HttpServletRequest request) {
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        String fileName = "";
        //先判断request中是否包涵multipart类型的数据，
        if(multipartResolver.isMultipart(request)){
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //这里的name为fileItem的alias属性值，相当于form表单中name
                String name = (String)iter.next();
                //根据name值拿取文件
                MultipartFile file = multiRequest.getFile(name);
                if(file != null){
                    String[] names = file.getOriginalFilename().split("\\.");
                    fileName = IdGen.uuid() + "." + names[1];
                    File localFile = new File(localFilepath + fileName);
                    if(!localFile.getParentFile().exists()) {
                        //如果目标文件所在的目录不存在，则创建父目录
                        localFile.getParentFile().mkdirs();
                    }
                    //写文件到本地
                    try {
                        file.transferTo(localFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
                    }
                }
            }
        }else {
            return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<Object>(fileServer + fileName, HttpStatus.OK);
    }
}
