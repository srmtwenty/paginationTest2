package com.scott.controllers;

import com.scott.models.*;
import com.scott.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/musics")
@CrossOrigin(origins="http://localhost:3000")
public class MusicController {
    @Autowired
    MusicRepository musicRepository;
    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    RoutineRepository routineRepository;
    @Autowired
    SoundtrackRepository soundtrackRepository;
    @Autowired
    SNSRepository snsRepository;
    @PostMapping("/create")
    public Music createMusic(@RequestBody Music music){
        return musicRepository.save(music);
    }
    @GetMapping
    public List<Music> findAllMusic(){
        return musicRepository.findAll();
    }


    @GetMapping("/{musicId}")
    public Music findMusicById(@PathVariable Long musicId){
        return musicRepository.findById(musicId).orElseThrow(()->new RuntimeException("Music has not been found!"));
    }
    @PutMapping("/{musicId}/update")
    public Music updateMusic(@PathVariable Long musicId, @RequestBody Music music){
        Music updateM=this.findMusicById(musicId);
        updateM.setName(music.getName());
        updateM.setDescription(music.getDescription());
        //updateM.setSoundtrack(music.getSoundtrack());
        //updateM.setArtists(music.getArtists());
        //updateM.setComposers(music.getComposers());
        return musicRepository.save(updateM);

    }
    @DeleteMapping("/{musicId}/delete")
    public void deleteMusic(@PathVariable Long musicId){
        musicRepository.deleteById(musicId);
    }

    @PutMapping("/{musicId}/addArtist/{artistId}")
    public Music addArtist(@PathVariable Long musicId, @PathVariable Long artistId){
        Music music=musicRepository.findById(musicId).get();
        Artist artist=artistRepository.findById(artistId).get();
        music.addArtist(artist);
        return musicRepository.save(music);
    }
    @PutMapping("/{musicId}/addComposer/{composerId}")
    public Music addComposer(@PathVariable Long musicId, @PathVariable Long composerId){
        Music music=musicRepository.findById(musicId).get();
        Artist composer=artistRepository.findById(composerId).get();
        music.addComposer(composer);
        return musicRepository.save(music);
    }
    @PutMapping("/{musicId}/removeArtist/{artistId}")
    public Music removeArtist(@PathVariable Long musicId, @PathVariable Long artistId){
        Music music=musicRepository.findById(musicId).get();
        Artist artist=artistRepository.findById(artistId).get();
        music.removeArtist(artist);
        return musicRepository.save(music);
    }
    @PutMapping("/{musicId}/removeComposer/{composerId}")
    public Music removeComposer(@PathVariable Long musicId, @PathVariable Long composerId){
        Music music=musicRepository.findById(musicId).get();
        Artist composer=artistRepository.findById(composerId).get();
        music.removeComposer(composer);
        return musicRepository.save(music);
    }

    @GetMapping("/{musicId}/routines")
    public Set<Routine> findRoutinesForMusic(@PathVariable Long musicId){
        Music music=musicRepository.findById(musicId).get();
        Set<Routine> routines=music.getRoutines();

        return routines;
    }

    @PutMapping("/{musicId}/assignSoundtrack/{soundtrackId}")
    public Music assignSoundtrack(@PathVariable Long musicId, @PathVariable Long soundtrackId){
        Music music=musicRepository.findById(musicId).get();
        Soundtrack soundtrack=soundtrackRepository.findById(soundtrackId).get();
        music.setSoundtrack(soundtrack);

        System.out.println("updated!");

        return musicRepository.save(music);
    }

    @PutMapping("/{musicId}/removeSoundtrack/{soundtrackId}")
    public Music removeSoundtrack(@PathVariable Long musicId, @PathVariable Long soundtrackId){
        Music music=musicRepository.findById(musicId).get();
        Soundtrack soundtrack=soundtrackRepository.findById(soundtrackId).get();
        music.removeSoundtrack(soundtrack);
        System.out.println("updated!");
        return musicRepository.save(music);
    }
    @GetMapping("/{offset}/{pageSize}/{field}")
    public Page<Music> findMusicsPaginationAndSorting(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return musicRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }
    @GetMapping("/{musicId}/snss")
    public Set<SNS> findAllSNSForMusic(@PathVariable Long musicId){
        Music music=musicRepository.findById(musicId).get();
        return music.getMusicLinks();
    }
    @PutMapping("/{musicId}/addSNS")
    public Music addSNS(@PathVariable Long musicId, @RequestBody SNS sns){
        Music music=musicRepository.findById(musicId).get();
        sns.setMusic(music);
        snsRepository.save(sns);
        music.addMusicLink(sns);
        return musicRepository.save(music);
    }
    @PutMapping("/{musicId}/removeSNS/{snsId}")
    public Music removeSNS(@PathVariable Long musicId, @PathVariable Long snsId){
        Music music=musicRepository.findById(musicId).get();
        SNS sns=snsRepository.findById(snsId).get();
        sns.removeMusic(music);
        //person.removeSNS(sns);
        snsRepository.save(sns);
        return musicRepository.save(music);
    }

}
