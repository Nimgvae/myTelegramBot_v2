package mytelegrambot_v2.controllers;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import mytelegrambot_v2.dto.StatisticDto;
import mytelegrambot_v2.services.impl.StatisticServiceImpl;
import mytelegrambot_v2.util.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controller class for managing statistic endpoints.
 */
@RestController
@RequestMapping("/statistic")
@AllArgsConstructor
public class StatisticController {

    private final StatisticServiceImpl statisticServiceImpl;
    /**
     * Retrieves statistic by ID.
     * @param id The ID of the statistic to retrieve.
     * @return SimpleResponse containing the statistic DTO or a not found response.
     */
    @GetMapping("/{id}/get_statistic")
    public SimpleResponse<?> getStatisticById(@PathVariable Long id){
        StatisticDto statDto = statisticServiceImpl.getStatisticById(id);
        if(statDto.equals(null)){
            return new SimpleResponse<>(HttpStatus.NOT_FOUND);
        }
        return new SimpleResponse<>(statDto);
    }
    /**
     * Retrieves all statistics.
     * @return SimpleResponse containing a list of statistic DTOs.
     */

    @GetMapping("/get-all_statistic")
    public SimpleResponse<?> getAllStatistic(){
        return new SimpleResponse<>(statisticServiceImpl.getAllStatistic());
    }

    /**
     * Adds a new statistic.
     * @param statisticBody The statistic DTO to add.
     * @return SimpleResponse indicating success or failure.
     */

    @PostMapping("/add_statistic")
    public SimpleResponse<?> addStatistic(@RequestBody @NonNull StatisticDto statisticBody){
        statisticServiceImpl.addStatistic(statisticBody);
        return new SimpleResponse<>(HttpStatus.OK);
    }
    /**
     * Updates an existing statistic by ID.
     * @param statisticDto The updated statistic DTO.
     * @param id The ID of the statistic to update.
     * @return SimpleResponse indicating success or failure.
     */

    @PutMapping("/{id}/update_statistic")
    public SimpleResponse<?> updateStatistic(@RequestBody @NonNull StatisticDto statisticDto, @PathVariable Long id){
        StatisticDto statDto = statisticServiceImpl.updateStatistic(statisticDto, id);
        if(statDto.equals(null)){
            return new SimpleResponse<>(HttpStatus.NOT_FOUND);
        }
        return new SimpleResponse<>(HttpStatus.OK);
    }
    /**
     * Deletes a statistic by ID.
     * @param id The ID of the statistic to delete.
     * @return ResponseEntity indicating success or failure.
     */
    @DeleteMapping("/{id}/delete_all_statistic")
    public ResponseEntity<Void> deleteStatistic(@PathVariable Long id) {
        statisticServiceImpl.deleteStatistic(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Deletes all statistics.
     * @return ResponseEntity indicating success or failure.
     */
    @DeleteMapping("/delete_all_statistic")
    public ResponseEntity<Void> deleteStatistic() {
        statisticServiceImpl.deleteAllStatistic();
        return ResponseEntity.noContent().build();
    }

}
