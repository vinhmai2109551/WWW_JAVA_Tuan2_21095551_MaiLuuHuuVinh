package bai2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

/**
 * 28-08-2025
 * Mai Lưu Hữu Vinh - 21095551
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/upload")
@MultipartConfig
public class MultiUploadServlet extends HttpServlet {


    private static final String UPLOAD_DIR = "C:/uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        StringBuilder fileNames = new StringBuilder();
        try {
            // Lặp qua tất cả các part được gửi lên có name="file"
            for (Part part : request.getParts()) {
                String fileName = part.getSubmittedFileName();
                if (fileName != null && !fileName.isEmpty()) {
                    String filePath = UPLOAD_DIR + File.separator + fileName;
                    part.write(filePath); // Lưu file
                    fileNames.append(fileName).append("<br>");
                }
            }
            if (fileNames.length() > 0) {
                request.setAttribute("message", "Các file đã được upload thành công:<br>" + fileNames.toString());
            } else {
                request.setAttribute("message", "Không có file nào được chọn để upload.");
            }
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi upload file: " + e.getMessage());
        }

        getServletContext().getRequestDispatcher("/uploadResult.jsp").forward(request, response);
    }
}
