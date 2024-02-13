package mytelegrambot_v2.repositories;

import mytelegrambot_v2.entity.MessagePattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagePatternRepository extends JpaRepository<MessagePattern, Long> {

    List<MessagePattern> getMessagePatternByAdvTrue();


}
