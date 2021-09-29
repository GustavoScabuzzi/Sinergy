package com.sinergy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sinergy.model.Postagem;

public interface RepositorioPostagem extends JpaRepository<Postagem, Long> {

}
