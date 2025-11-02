package Students.example.Students.service;

import Students.example.Students.entity.Pack;
import Students.example.Students.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackService {
    
    @Autowired
    private PackRepository packRepository;
    
    public List<Pack> getAllPacks() {
        return packRepository.findAll();
    }
    
    public Optional<Pack> getPackById(Long id) {
        return packRepository.findById(id);
    }
    
    public List<Pack> getPacksByYear(Integer year) {
        return packRepository.findByYear(year);
    }
    
    public List<Pack> getPacksByYearAndSemester(Integer year, Integer semester) {
        return packRepository.findByYearAndSemester(year, semester);
    }
    
    public List<Pack> getPacksByNameContaining(String name) {
        return packRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Pack> getPacksWithCourses() {
        return packRepository.findPacksWithCourses();
    }
    
    public Object[] getPackWithCourseCount(Long packId) {
        return packRepository.findPackWithCourseCount(packId);
    }
    
    public Pack savePack(Pack pack) {
        return packRepository.save(pack);
    }
    
    public void deletePack(Long id) {
        packRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return packRepository.existsById(id);
    }
    
    public long countPacks() {
        return packRepository.count();
    }
}
