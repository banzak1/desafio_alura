package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TaskService {

    private final CourseRepository courseRepository;
    private final OpenTextTaskRepository openTextTaskRepository;

    public TaskService(CourseRepository courseRepository, OpenTextTaskRepository openTextTaskRepository) {
        this.courseRepository = courseRepository;
        this.openTextTaskRepository = openTextTaskRepository;
    }

    @Transactional
    public ResponseEntity<?> createOpenTextTask(OpenTextTaskDTO dto){
        Course course = courseRepository.findById(dto.getCourseId()).orElseThrow(()->new EntityNotFoundException("Curso não encontrado"));

        if (!course.getStatus().equals(Status.BUILDING)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O curso deve estar no status BUILDING para adicionar atividades.");
        }

        boolean statementExists = openTextTaskRepository.existsByCourseIdAndStatement(course, dto.getStatement());
        if (statementExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe uma atividade com o mesmo enunciado no curso.");
        }

        List<OpenTextTask> tasks = openTextTaskRepository.findByCourseId(course);
        tasks.sort(Comparator.comparingLong(OpenTextTask::getOrder));

        for(OpenTextTask existingTask : tasks){
            if (existingTask.getOrder() >= dto.getOrder()) {
                existingTask.setOrder(existingTask.getOrder() + 1);
                openTextTaskRepository.save(existingTask);
            }
        }

        OpenTextTask task = new OpenTextTask();
        task.setCourseId(course);
        task.setStatement(dto.getStatement());
        task.setOrder(dto.getOrder());

        OpenTextTask saved = openTextTaskRepository.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
