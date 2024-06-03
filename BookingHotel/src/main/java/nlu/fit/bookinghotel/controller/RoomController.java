package nlu.fit.bookinghotel.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nlu.fit.bookinghotel.DTO.MultipleRoomNumberDTO;
import nlu.fit.bookinghotel.DTO.RoomDTO;
import nlu.fit.bookinghotel.DTO.RoomImageDTO;
import nlu.fit.bookinghotel.entity.Room;

import nlu.fit.bookinghotel.entity.RoomImage;
import nlu.fit.bookinghotel.reponses.RoomListResponse;
import nlu.fit.bookinghotel.reponses.RoomResponse;
import nlu.fit.bookinghotel.service.IRoomService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService roomService;


    @PostMapping("/insert")
    //POST http://localhost:8088/room/insert
    public ResponseEntity<?> createRoom(
            @Valid @RequestBody RoomDTO roomDTO,
            BindingResult result
    ){
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Room newRoom = roomService.createRoom(roomDTO);
            return ResponseEntity.ok(newRoom);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("/batchInsert")
    public ResponseEntity<?> createMultipleRooms(
            @Valid @RequestBody MultipleRoomNumberDTO multipleRoomDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            List<Room> newRooms = multipleRoomDTO.getRoomNumbers()
                    .stream()
                    .map(roomNumber -> {
                        RoomDTO roomDTO = RoomDTO.builder()
                                .roomNumber(roomNumber)
                                .title(multipleRoomDTO.getTitle())
                                .price_per_night(multipleRoomDTO.getPrice_per_night())
                                .price_per_hour(multipleRoomDTO.getPrice_per_hour())
                                .thumbnail(multipleRoomDTO.getThumbnail())
                                .description(multipleRoomDTO.getDescription())
                                .hotelId(multipleRoomDTO.getHotelId())
                                .build();
                        try {
                            return roomService.createRoom(roomDTO);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();

            return ResponseEntity.ok(newRooms);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //POST http://localhost:8088/room
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long roomId,
            @ModelAttribute("files") List<MultipartFile> files
    ){
        try {
            Room existingProduct = roomService.getRoomById(roomId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if(files.size() > RoomImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body("UPLOAD_IMAGES_MAX_5");
            }
            List<RoomImage> roomImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if(file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if(file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("UPLOAD_IMAGES_FILE_LARGE");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("UPLOAD_IMAGES_FILE_MUST_BE_IMAGE");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB
                RoomImage roomImage = roomService.createRoomImage(
                        existingProduct.getId(),
                        RoomImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                roomImages.add(roomImage);
            }
            return ResponseEntity.ok().body(roomImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    @GetMapping("")
    public ResponseEntity<RoomListResponse> getProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "hotel_id") Long hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        // Tạo Pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sort.by("createdAt").descending()
                Sort.by("id").ascending()
        );
        Page<RoomResponse> roomPage = roomService.getAllRooms(keyword, hotelId, pageRequest);
        // Lấy tổng số trang
        int totalPages = roomPage.getTotalPages();
        List<RoomResponse> rooms = roomPage.getContent();
        return ResponseEntity.ok(RoomListResponse
                .builder()
                .rooms(rooms)
                .totalPages(totalPages)
                .build());
    }
}