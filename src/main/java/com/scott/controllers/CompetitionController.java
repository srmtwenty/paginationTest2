package com.scott.controllers;

import com.scott.models.*;
import com.scott.repositories.CompetitionRepository;
import com.scott.repositories.NationRepository;
import com.scott.repositories.NationalTeamRepository;
import com.scott.repositories.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competitions")
@CrossOrigin(origins="http://localhost:3000")
public class CompetitionController {

    @Autowired
    CompetitionRepository competitionRepository;
    @Autowired
    RoutineRepository routineRepository;
    @Autowired
    NationalTeamRepository nationalTeamRepository;
    @Autowired
    NationRepository nationRepository;
    @PostMapping("/create")
    public Competition createCompetition(@RequestBody Competition competition){
        return competitionRepository.save(competition);
    }
    @GetMapping
    public List<Competition> findAllCompetitions(){
        return competitionRepository.findAll();
    }
    @GetMapping("/{competitionId}")
    public Competition findCompetitionById(@PathVariable Long competitionId){
        return competitionRepository.findById(competitionId).orElseThrow(()->new RuntimeException("Competition has not been found!"));
    }
    @PutMapping("/{competitionId}/update")
    public Competition updateCompetition(@PathVariable Long competitionId, @RequestBody Competition competition){
        Competition updateC=this.findCompetitionById(competitionId);
        updateC.setName(competition.getName());
        updateC.setDescription(competition.getDescription());
        //updateC.setNation(competition.getNation());
        //updateC.setDate(competition.getDate());
        updateC.setLocation(competition.getLocation());
        //updateC.setParticipatedTeams(competition.getParticipatedTeams());
        //updateC.setRoutines(competition.getRoutines());
        return competitionRepository.save(updateC);
    }
    @DeleteMapping("/{competitionId}/delete")
    public void deleteCompetition(@PathVariable Long competitionId){
        competitionRepository.deleteById(competitionId);
    }

    @PutMapping("/{competitionId}/addRoutine/{routineId}")
    public Competition addRoutine(@PathVariable Long competitionId, @PathVariable Long routineId){
        Competition updateC=competitionRepository.findById(competitionId).get();
        Routine routine=routineRepository.findById(routineId).get();
        updateC.addRoutine(routine);
        return competitionRepository.save(updateC);
    }
    @PutMapping("/{competitionId}/removeRoutine/{routineId}")
    public Competition removeRoutine(@PathVariable Long competitionId, @PathVariable Long routineId){
        Competition updateC=competitionRepository.findById(competitionId).get();
        Routine routine=routineRepository.findById(routineId).get();
        updateC.removeRoutine(routine);
        return competitionRepository.save(updateC);
    }
    @PutMapping("/{competitionId}/addNationalTeam/{nationalTeamId}")
    public Competition addNationalTeam(@PathVariable Long competitionId, @PathVariable Long nationalTeamId){
        Competition updateC=competitionRepository.findById(competitionId).get();
        NationalTeam nationalTeam=nationalTeamRepository.findById(nationalTeamId).get();
        updateC.addNationalTeam(nationalTeam);
        return competitionRepository.save(updateC);
    }
    @PutMapping("/{competitionId}/removeNationalTeam/{nationalTeamId}")
    public Competition removeNationalTeam(@PathVariable Long competitionId, @PathVariable Long nationalTeamId){
        Competition updateC=competitionRepository.findById(competitionId).get();
        NationalTeam nationalTeam=nationalTeamRepository.findById(nationalTeamId).get();
        updateC.removeNationalTeam(nationalTeam);
        return competitionRepository.save(updateC);
    }

    @PutMapping("/{competitionId}/assignNation/{nationId}")
    public Competition assignNation(@PathVariable Long competitionId, @PathVariable Long nationId){
        Competition competition=competitionRepository.findById(competitionId).get();
        Nation nation=nationRepository.findById(nationId).get();
        competition.setNation(nation);
        return competitionRepository.save(competition);
    }
    @PutMapping("/{competitionId}/removeNation/{nationId}")
    public Competition removeNation(@PathVariable Long competitionId, @PathVariable Long nationId){
        Competition competition=competitionRepository.findById(competitionId).get();
        Nation nation=nationRepository.findById(nationId).get();
        competition.removeNation(nation);
        return competitionRepository.save(competition);
    }


    @GetMapping("/{offset}/{pageSize}/{field}")
    public Page<Competition> findCompetitionsPaginationAndSorting(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return competitionRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }
}
