package edu.pucmm.eict.sm64sb.repositorios;

import edu.pucmm.eict.sm64sb.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {


}
