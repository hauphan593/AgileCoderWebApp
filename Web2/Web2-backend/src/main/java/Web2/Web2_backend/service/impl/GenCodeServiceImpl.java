package Web2.Web2_backend.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Web2.Web2_backend.dto.GenCodeDto;
import Web2.Web2_backend.entity.GenCode;
import Web2.Web2_backend.exception.ResourceNotFoundException;
import Web2.Web2_backend.exception.RuntimeErrorException;
import Web2.Web2_backend.mapper.GenCodeMapper;
import Web2.Web2_backend.repository.GenCodeRepository;
import Web2.Web2_backend.repository.UserRepository;
import Web2.Web2_backend.service.GenCodeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenCodeServiceImpl implements GenCodeService {

    private GenCodeRepository genCodeRepository;
    private UserRepository userRepository;

    private File genFile(GenCodeDto genCodeDto, String timeStr) {

        // Đường dẫn đến thư mục chứa file Python
        
        //code python test 1/3
        String directoryPath = "C:\\Users\\haupq\\Desktop\\Project\\project_python";

        //real code 1/3
        // String directoryPath = 
        //     "C:\\Users\\haupq\\Desktop\\Project\\AgileCoder-main\\agilecoder";
   

        // Đường dẫn đến thư mục warehouse nơi Python lưu file kết quả
        
        // code test 2/3
        String warehousePath = "C:\\Users\\haupq\\Desktop\\Project\\project_python\\warehouse";
        
        //real code 2/3
        // String warehousePath = 
        // "C:\\Users\\haupq\\Desktop\\Project\\AgileCoder-main\\agilecoder\\WareHouse";

        // File ZIP sẽ được tạo
        //lay thoi gian tao
        // LocalDateTime now = LocalDateTime.now();
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        // String timeStr = now.format(formatter);

//      tao ten file zip
        String zipFileName = 
            // genCodeDto.getProjectName() + "_" +
            // genCodeDto.getCompanyName() + "_" + 
            // timeStr + ".zip";
            "Project_Company_" + timeStr + ".zip";

//      test code 3/3
        String[] command = {
            "python", "run.py"
        };


        // String[] command = {
        //     "python", "run.py",
        //     "--org", "\"" + genCodeDto.getCompanyName() + "\"", 
        //     "--name", "\"" + genCodeDto.getProjectName() + "\"", 
        //     "--task", "\"" + genCodeDto.getProjectRequire() + "\""
        // };
//      real code 3/3
        // String[] command = {
        //     "python", "run.py",
        //     "--org", "\" Company name \"", 
        //     "--name", "\" Project name  \"", 
        //     "--task", "\"" + genCodeDto.getProjectRequire() + "\""
        // };


        try {
                //khoi chay scripts Python
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.directory(new File(directoryPath));
                
                //luu thong bao python vao file txt
                // File outputFile = 
                //     new File("C:\\Users\\haupq\\Desktop\\Project\\AgileCoderWebApp\\Web2\\Web2-backend\\src\\main\\java\\Web2\\Web2_backend\\error_Log\\output.log");
                // File errorFile = 
                //     new File("C:\\Users\\haupq\\Desktop\\Project\\AgileCoderWebApp\\Web2\\Web2-backend\\src\\main\\java\\Web2\\Web2_backend\\error_Log\\error.log");
                // processBuilder.redirectOutput(outputFile);
                // processBuilder.redirectError(errorFile);

                //run pythoncode
                Process process = processBuilder.start();

                // read output neu co
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
                );
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                //read error neu co
                BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream())
                );
                while ((line = errorReader.readLine()) != null) {
                    System.err.println(line);
                }

                //wait for python complete
                int exitCode = process.waitFor();
                System.out.println("Python process exited with code: "+ exitCode);

                //find newest file
                File warehouseDir = new File(warehousePath);
                File latestFolder = getLatestFolder(warehouseDir);
                //for test case
                if(latestFolder != null) {
                    System.out.println("File found: "+ latestFolder.getAbsolutePath());

                    //compress file
                    File zipFolder = new File(warehousePath,zipFileName);
                    zipFolder(latestFolder,zipFolder);
                    System.out.println("Copressed success:"+ zipFolder.getAbsolutePath());
                    return zipFolder;
            } else {
                System.err.println("file not found");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    //fine lastest file method
    private static File getLatestFolder(File directory){
        File[] folders = directory.listFiles(File::isDirectory);
        if (folders == null || folders.length == 0) {
            return null;
        }
        File lastestFolder = folders[0];
        for (File folder : folders) {
            if (folder.lastModified() > lastestFolder.lastModified()) {
                lastestFolder = folder;
            }
        }
        return lastestFolder;
    }

    //Compress folder into zip file
    private static void zipFolder(File folder, File zipFile) {
        try(FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos)) {
                zipFolderRecursive(folder, folder.getName(),zos);
                System.out.println("folder compressed success " + zipFile.getName());
                 
            } catch (IOException e) {
                System.err.println("cant copress error occor "+ e.getMessage());
                e.printStackTrace();
            }
    }

    private static void zipFolderRecursive(File folder, String basePath, ZipOutputStream zos) throws IOException {
        File[] files = folder.listFiles();
        if (files == null) return;

        for(File file : files) {
            String path = basePath + "\\" +file.getName();
            if (file.isDirectory()) {
                zipFolderRecursive(file, path, zos);
            } else {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(path);
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }zos.closeEntry();
                }
            }
        }
    }

    @Override
    public GenCodeDto createGenCode(GenCodeDto genCodeDto) {
        //validate zip file
        try {

            //lay thoi gian tao
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String timeStr = now.format(formatter);
            File file = genFile(genCodeDto, timeStr);
            if (file == null)
            {
                throw new RuntimeErrorException("no file found" );
            }
            //tao duong dan
            String uploadDir = "C:\\Users\\haupq\\Desktop\\Project\\AgileCoderWebApp\\Web2"+
                                "\\Web2-backend\\src\\main\\resources\\DownloadedGenCode";
            //check file trung
            File targetFolder = new File(uploadDir);
            File uniquFile = UniqueFile(targetFolder, file.getName());
            
            String filePath = uploadDir + "\\" + uniquFile.getName();
            //luu file
            //Tao Dir if not found
            Files.createDirectories(Paths.get(uploadDir));
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            Files.write(Paths.get(filePath), fileBytes);
            // Files.write(Paths.get(fithPath), file.read);

            GenCode genCode = GenCodeMapper.mapToGenCode(genCodeDto, userRepository);
            genCode.setFileData(filePath);
            genCode.setCompanyName("Company_" + timeStr);  
            genCode.setProjectName("project_"+ timeStr); 
            
            //get user id
            Long uId = (long) 1;
            genCode.setUser(userRepository.findById(uId)
                            .orElseThrow(() -> new ResourceNotFoundException("Role is not exists with given id: "  )));
            genCode.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            genCode.setCreatedBy("admin");
            genCode.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            genCode.setUpdatedBy("admin");

            GenCode savedGenCode = genCodeRepository.save(genCode);
            return GenCodeMapper.mapToGenCodeDto(savedGenCode);
        } catch (IOException e) {
            throw new RuntimeErrorException("error while saving file :" + e.getMessage());
        }
    }

    @Override
    public GenCodeDto getGenCodebyId(Long projectId) {
        GenCode genCode = genCodeRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project is not exists with given id: " + projectId));
        return GenCodeMapper.mapToGenCodeDto(genCode);
    }

    @Override
    public List<GenCodeDto> getAllGenCode() {
        List<GenCode> genCodes = genCodeRepository.findAll();
        return genCodes.stream().map((genCode) -> GenCodeMapper.mapToGenCodeDto(genCode))
                .collect(Collectors.toList());
    }

    @Override
    public GenCodeDto updateGenCode(Long projectId, GenCodeDto updatedGenCode) {
        GenCode genCode = genCodeRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project is not exists with given id: " + projectId));
        //update data
        genCode.setCompanyName(updatedGenCode.getCompanyName());
        genCode.setProjectName(updatedGenCode.getProjectName());

        //update times and author
        genCode.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        genCode.setUpdatedBy("admin");

        GenCode updatedGenCode2 = genCodeRepository.save(genCode);
        return GenCodeMapper.mapToGenCodeDto(updatedGenCode2);
    }

    @Override
    public void deleteGenCode(Long projectId) {
        GenCode genCode = genCodeRepository.findById(projectId)
                            .orElseThrow(() -> new ResourceNotFoundException("Project is not exists with given id: " + projectId));
        genCode.setUser(null);
        genCodeRepository.save(genCode);
        genCodeRepository.deleteById(projectId);
    }

    private File UniqueFile(File targetFolder, String originFileName) {
        File targetFile = new File(targetFolder,originFileName);
        int count = 1;

        // vong lap dat ten theo so
        while (targetFile.exists()) {
            String fileNamewithoutExt = originFileName.contains(".") 
                    ? originFileName.substring(0,originFileName.lastIndexOf('.')): originFileName;
            String fileExtension = originFileName.contains(".")
                    ? originFileName.substring(originFileName.lastIndexOf('.')):"";
            targetFile = new File(targetFolder, fileNamewithoutExt + "("+ count + ")"+fileExtension);
            count++;
        }
        return targetFile;
    }

    @Override
    public void DownloadedGenCode(Long projectId) {
        GenCode genCode = genCodeRepository.findById(projectId)
        .orElseThrow(() -> new ResourceNotFoundException("Project is not exists with given id: " + projectId));
        try {   
            //Get file from db
            Path filePath = Paths.get(genCode.getFileData());
            File sourceFile = filePath.toFile();
            // ensure source file exists
            if (!sourceFile.exists()) {
                System.out.println("sau khi save Gencode " ); 
                throw new ResourceNotFoundException("Project not found" + projectId);
            }
            
            File targetFolder = new File("C:\\Users\\haupq\\Downloads");
            if(!targetFolder.exists()) {
                targetFolder.mkdirs();
            }



            File targetFile = UniqueFile(targetFolder, sourceFile.getName());
            Files.copy(filePath, targetFile.toPath());
        } catch (Exception e) {
            throw new RuntimeErrorException("error while down file :" + e.getMessage());
        }
    }

}
