package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpenTextTaskRepository extends JpaRepository<OpenTextTask, Long> {
    List<OpenTextTask> findByCourseId(Course courseId);

    boolean existsByCourseIdAndStatement(Course course, String statement);
}
