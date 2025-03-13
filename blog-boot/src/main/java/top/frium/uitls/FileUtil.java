package top.frium.uitls;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 文件操作工具类
 */
@SuppressWarnings("all")
public class FileUtil {
	/**
	 * 读取文件内容为二进制数组
	 *
	 * @param filePath 文件路径
	 * @return 文件内容的二进制数组
	 * @throws IOException 如果发生 I/O 错误
	 */
	public static byte[] read(MultipartFile file) throws IOException {
		InputStream in = file.getInputStream();
		byte[] data = inputStream2ByteArray(in);
		in.close();
		return data;
	}

	public static String convertMultipartFileToString(MultipartFile file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();

		try (InputStream inputStream = file.getInputStream();
			 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}

		return stringBuilder.toString();
	}
	/**
	 * 流转二进制数组
	 *
	 * @param in 输入流
	 * @return 二进制数组
	 * @throws IOException 如果发生 I/O 错误
	 */
	static byte[] inputStream2ByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}

	/**
	 * 保存文件
	 *
	 * @param filePath 文件路径
	 * @param fileName 文件名
	 * @param content 文件内容的二进制数组
	 */
	public static void save(String filePath, String fileName, byte[] content) {
		try {
			// 创建文件目录（如果不存在）
			File filedir = new File(filePath);
			if (!filedir.exists()) {
				filedir.mkdirs();
			}
			// 创建文件对象
			File file = new File(filedir, fileName);
			// 创建输出流并写入内容
			OutputStream os = new FileOutputStream(file);
			os.write(content, 0, content.length);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace(); // 捕获文件未找到异常并打印堆栈跟踪
		} catch (IOException e) {
			e.printStackTrace(); // 捕获 I/O 异常并打印堆栈跟踪
		}
	}
	//获取文件名

}
