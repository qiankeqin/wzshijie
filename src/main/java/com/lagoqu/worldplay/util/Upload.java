package com.lagoqu.worldplay.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.event.IIOWriteProgressListener;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sun.misc.BASE64Decoder;

import com.lagoqu.common.util.DateUtil;
import com.lagoqu.worldplay.constants.Constants;

public class Upload {

	public static JSONArray uploadrelative(HttpServletRequest request, String relativeFolder) {
		// String relativeName = String.format("%s/%s",
		// Constants.upload_img_path, relativeFolder);
		String spilt = File.separator;
		JSONArray jArray=new JSONArray();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			request.setCharacterEncoding("utf-8");
			List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
			Iterator<FileItem> i = items.iterator();
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				String fileName = fi.getName();
				if (fileName != null) {
					String filelast = fileName.substring(fileName.lastIndexOf(".") + 1);
					String randomfilename=UUID.randomUUID().toString();
					String newFileName = null;
					// 1：图片 2.音频文件
					String uploadPath = ""; 
					if (fileName.contains(".amr")) {
						uploadPath = String.format("%s%saudio%s%s%s%s",Constants.upload_img_path, spilt, spilt,
								relativeFolder, spilt, DateUtil.getDateYyyymm(new Date()));

					} else {
						uploadPath = String.format("%s%simg%s%s%s%s",Constants.upload_img_path, spilt, spilt,
								relativeFolder, spilt, DateUtil.getDateYyyymm(new Date()));
						if(filelast.toLowerCase().equals("jpeg")){
							filelast="jpg";
						}
					}
					
					File mPath = new File(String.format("%s%s%s", request.getServletContext().getRealPath("/"),spilt,uploadPath));
					if (!mPath.exists()) {
						mPath.mkdirs();
					}
					newFileName = String.format("%s.%s", randomfilename, filelast);

					File savedFile = new File(mPath, newFileName);
					
					if(relativeFolder.equals("weixin")){
						File savedFile1 = new File(mPath, String.format("%s.%s", randomfilename+"del", filelast));
						fi.write(savedFile1);
						BufferedImage image = ImageIO.read(savedFile1);
						writeJPEG(savedFile, image, 18, null);
					}else{
						fi.write(savedFile);
					}
					// System.out.print("图片路径：" + uploadPath +
					// File.separator + newFileName);
					String outFileName = String.format("%s%s%s%s", spilt,uploadPath, spilt, newFileName);
					System.out.print("相对路径：" + outFileName);
					outFileName=outFileName.replace("\\", "/");
					jArray.add(outFileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jArray;
	}
	
	public static JSONArray uploadrelativeRotat(HttpServletRequest request, String relativeFolder,String rotat) {
		// String relativeName = String.format("%s/%s",
		// Constants.upload_img_path, relativeFolder);
		String spilt = File.separator;
		JSONArray jArray=new JSONArray();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			request.setCharacterEncoding("utf-8");
			List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
			Iterator<FileItem> i = items.iterator();
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				String fileName = fi.getName();
				if (fileName != null) {
					String filelast = fileName.substring(fileName.lastIndexOf(".") + 1);
					String randomfilename=UUID.randomUUID().toString();
					String newFileName = null;
					// 1：图片 2.音频文件
					String uploadPath = ""; 
					if (fileName.contains(".amr")) {
						uploadPath = String.format("%s%saudio%s%s%s%s",Constants.upload_img_path, spilt, spilt,
								relativeFolder, spilt, DateUtil.getDateYyyymm(new Date()));

					} else {
						uploadPath = String.format("%s%simg%s%s%s%s",Constants.upload_img_path, spilt, spilt,
								relativeFolder, spilt, DateUtil.getDateYyyymm(new Date()));
						if(filelast.toLowerCase().equals("jpeg")){
							filelast="jpg";
						}
					}
					
					File mPath = new File(String.format("%s%s%s", request.getServletContext().getRealPath("/"),spilt,uploadPath));
					if (!mPath.exists()) {
						mPath.mkdirs();
					}
					newFileName = String.format("%s.%s", randomfilename, filelast);

					File savedFile = new File(mPath, newFileName);
					
					if(relativeFolder.equals("weixin")){
						File savedFile1 = new File(mPath, String.format("%s.%s", randomfilename+"del", filelast));
						fi.write(savedFile1);
						BufferedImage image = ImageIO.read(savedFile1);
						image = Rotate(image,Integer.parseInt(rotat));
						writeJPEG(savedFile, image, 18, null);
					}else{
						fi.write(savedFile);
					}
					String outFileName = String.format("%s%s%s%s", spilt,uploadPath, spilt, newFileName);
					System.out.print("相对路径：" + outFileName);
					outFileName=outFileName.replace("\\", "/");
					jArray.add(outFileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jArray;
	}
	
	
	
	
	
	
	/**
     * 保存图像到 JPEG 文件
     * @param file 保存的目标文件
     * @param image 保存的源图像
     * @param quality 保存的 JPEG 图像质量
     * @param listener 保存进度监听器
     */
    public static void writeJPEG(File file, BufferedImage image, int quality, IIOWriteProgressListener listener) throws
            FileNotFoundException, IOException {
        Iterator it = ImageIO.getImageWritersBySuffix("jpg");
        if (it.hasNext()) {
            FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(file);
            ImageWriter iw = (ImageWriter) it.next();
            ImageWriteParam iwp = iw.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality((float) quality / 100f);
            iw.setOutput(fileImageOutputStream);
            iw.addIIOWriteProgressListener(listener);
            iw.write(null, new IIOImage(image, null, null), iwp);
            iw.dispose();
            fileImageOutputStream.flush();
            fileImageOutputStream.close();
        }
    }
    /**
     * 方法名称: rotateImage<br>
     * 描述：图片旋转
     * 作者: 邢留杰
     * 修改日期：2015年8月10日下午2:58:56
     * @param bufferedimage
     * @param degree
     * @return
     */
    public static BufferedImage Rotate(BufferedImage src, int angel) {  
        int src_width = src.getWidth(null);  
        int src_height = src.getHeight(null);  
        // calculate the new image size  
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(  
                src_width, src_height)), angel);  
  
        BufferedImage res = null;  
        res = new BufferedImage(rect_des.width, rect_des.height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2 = res.createGraphics();  
        // transform  
        g2.translate((rect_des.width - src_width) / 2,  
                (rect_des.height - src_height) / 2);  
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);  
  
        g2.drawImage(src, null, null);  
        return res;  
    }  
    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {  
        // if angel is greater than 90 degree, we need to do some conversion  
        if (angel >= 90) {  
            if(angel / 90 % 2 == 1){  
                int temp = src.height;  
                src.height = src.width;  
                src.width = temp;  
            }  
            angel = angel % 90;  
        }  
  
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;  
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;  
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;  
        double angel_dalta_width = Math.atan((double) src.height / src.width);  
        double angel_dalta_height = Math.atan((double) src.width / src.height);  
  
        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha  
                - angel_dalta_width));  
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha  
                - angel_dalta_height));  
        int des_width = src.width + len_dalta_width * 2;  
        int des_height = src.height + len_dalta_height * 2;  
        return new java.awt.Rectangle(new Dimension(des_width, des_height));  
    }  
    
    
    
    
	/**
	 * 根据相对路径上传图片 mengxg
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param relativeFolder
	 *            相对公共路径地址，例如公共路径/upload/img 加上相对路径：/upload/img/banner
	 * @return
	 */
	public List<String> uploadrelative1(HttpServletRequest request, String relativeFolder) {
		String relativeName = String.format("%s/%s", Constants.upload_img_path_bms, relativeFolder);
		String filename = String.format("%s/%s", request.getServletContext().getRealPath("/"), relativeName);
		File uploadPath = new File(filename + "/");
		List<String> imageList = new ArrayList<String>();
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		// 临时文件目录
		File tempPathFile = new File((String) request.getRealPath("bmsphoto") + "/");
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(409600000); // 设置缓冲区大小，这里是409600000kb
			factory.setRepository(tempPathFile);// 设置缓冲区目录
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB
			List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
			Iterator<FileItem> i = items.iterator();
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				String fileName = fi.getName();
				if (fileName != null) {
					long currentTime = System.currentTimeMillis();
					String newFileName = "wzshijie_zhuli_img" + Long.toString(currentTime) + ".jpg";
					File savedFile = new File(uploadPath, newFileName);
					File savedFile1 = new File(uploadPath, String.format("%s.%s", newFileName+"del", "jpg"));
					fi.write(savedFile1);
					BufferedImage image = ImageIO.read(savedFile1);
					writeJPEG(savedFile, image, 100, null);
					System.out.print("图片路径：" + uploadPath + File.separator + newFileName);
					String outFileName = String.format("%s/%s", relativeName, newFileName);
					System.out.print("相对路径：" + outFileName);
					imageList.add(outFileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageList;
	}
	public List<String> uploadrelative2(HttpServletRequest request, String relativeFolder) {
		String relativeName = String.format("%s/%s", Constants.upload_img_path_bms, relativeFolder);
		String filename = String.format("%s/%s", request.getServletContext().getRealPath("/"), relativeName);
		File uploadPath = new File(filename + "/");
		List<String> imageList = new ArrayList<String>();
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		// 临时文件目录
		File tempPathFile = new File((String) request.getRealPath("bmsphoto") + "/");
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(409600000); // 设置缓冲区大小，这里是409600000kb
			factory.setRepository(tempPathFile);// 设置缓冲区目录
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB
			List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
			Iterator<FileItem> i = items.iterator();
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				String fileName = fi.getName();
				if (fileName != null) {
					long currentTime = System.currentTimeMillis();
					String newFileName = "wzshijie_zhuli_img" + Long.toString(currentTime) + ".jpg";
					File savedFile = new File(uploadPath, newFileName);
					fi.write(savedFile);
					String outFileName = String.format("%s/%s", relativeName, newFileName);
					System.out.print("相对路径：" + outFileName);
					imageList.add(outFileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageList;
	}

	public static JSONArray uploadBase64(HttpServletRequest request,String imgStr) {
		String spilt = File.separator;
		BASE64Decoder decoder = new BASE64Decoder(); 
		JSONArray jArray=new JSONArray();
		try{
			String[] imgArr = imgStr.split(";");
			String filelast = imgArr[0].split("/")[1];//后缀
			String randomfilename=UUID.randomUUID().toString();
			String baseStr = imgArr[1].split(",")[1];
	        byte[] b = decoder.decodeBuffer(baseStr);  
	        for(int i=0;i<b.length;++i)  
	        {  
	            if(b[i]<0)  
	            {
	                b[i]+=256;  
	            }  
	        }  
	        String uploadPath = ""; 
	        String newFileName = null;
	        String newTempFileName = null;
	        uploadPath = String.format("%s%simg%s%s%s%s",Constants.upload_img_path, spilt, spilt,"weixin", spilt, DateUtil.getDateYyyymm(new Date()));
			if(filelast.toLowerCase().equals("jpeg")){
				filelast="jpg";
			}
			File mPath = new File(String.format("%s%s%s", request.getServletContext().getRealPath("/"),spilt,uploadPath));
			if (!mPath.exists()) {
				mPath.mkdirs();
			}
			newTempFileName = String.format("%s.%s", randomfilename, filelast);
			newFileName = String.format("%s%s%s.%s", mPath,spilt,randomfilename,filelast);
	        OutputStream out = new FileOutputStream(newFileName);      
	        out.write(b);  
	        out.flush();  
	        out.close();  
	        String outFileName = String.format("%s%s%s%s", spilt,uploadPath, spilt, newTempFileName);
	        outFileName=outFileName.replace("\\", "/");
	        jArray.add(outFileName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jArray;
	}
}
