package StableMatch.example.StableMatch.service;

import StableMatch.example.StableMatch.dto.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StableMatchingService {

    public MatchingResponse solve(MatchingRequest request) {
        Map<String, Integer> courseCapacities = new HashMap<>();
        for (Course course : request.getCourses()) {
            courseCapacities.put(course.getId(), course.getCapacity());
        }

        List<Assignment> assignments = new ArrayList<>();
        Map<String, String> studentAssignments = new HashMap<>();
        Map<String, List<String>> courseAssignments = new HashMap<>();

        for (Course course : request.getCourses()) {
            courseAssignments.put(course.getId(), new ArrayList<>());
        }

        Queue<String> unassignedStudents = new LinkedList<>();
        Map<String, Integer> studentProposalIndex = new HashMap<>();

        for (Student student : request.getStudents()) {
            unassignedStudents.add(student.getId());
            studentProposalIndex.put(student.getId(), 0);
        }

        Map<String, List<String>> studentPreferences = new HashMap<>();
        for (Student student : request.getStudents()) {
            studentPreferences.put(student.getId(), student.getPreferences());
        }

        while (!unassignedStudents.isEmpty()) {
            String student = unassignedStudents.poll();
            List<String> preferences = studentPreferences.get(student);
            int proposalIndex = studentProposalIndex.get(student);

            if (proposalIndex >= preferences.size()) {
                continue;
            }

            String preferredCourse = preferences.get(proposalIndex);
            studentProposalIndex.put(student, proposalIndex + 1);

            List<String> currentAssignments = courseAssignments.get(preferredCourse);
            int capacity = courseCapacities.get(preferredCourse);

            if (currentAssignments.size() < capacity) {
                currentAssignments.add(student);
                studentAssignments.put(student, preferredCourse);
            } else {
                String lastStudent = currentAssignments.get(currentAssignments.size() - 1);
                currentAssignments.remove(currentAssignments.size() - 1);
                currentAssignments.add(student);
                studentAssignments.remove(lastStudent);
                studentAssignments.put(student, preferredCourse);
                unassignedStudents.add(lastStudent);
            }
        }

        for (Map.Entry<String, String> entry : studentAssignments.entrySet()) {
            assignments.add(new Assignment(entry.getKey(), entry.getValue()));
        }

        MatchingResponse response = new MatchingResponse();
        response.setAssignments(assignments);
        response.setStable(true);
        return response;
    }
}
