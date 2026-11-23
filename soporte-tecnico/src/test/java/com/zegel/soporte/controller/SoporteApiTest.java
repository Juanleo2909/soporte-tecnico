package com.zegel.soporte.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SoporteApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void ejecutaFlujoCrudDeClienteTecnicoYSolicitud() throws Exception {
        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Andrea Salazar",
                                  "email": "asalazar@empresa.pe",
                                  "telefono": "987112233",
                                  "empresa": "Inversiones Lima SAC"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("asalazar@empresa.pe"))
                .andExpect(jsonPath("$.empresa").value("Inversiones Lima SAC"));

        mockMvc.perform(post("/api/v1/tecnicos")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Luis Técnico",
                                  "email": "luis@empresa.pe",
                                  "especialidad": "Hardware"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(post("/api/v1/solicitudes")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "descripcion": "Revisar equipo",
                                  "clienteId": 1,
                                  "tecnicoId": 1
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.tecnico.id").value(1));

        mockMvc.perform(get("/api/v1/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        mockMvc.perform(put("/api/v1/solicitudes/1")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "descripcion": "Equipo reparado",
                                  "clienteId": 1,
                                  "tecnicoId": 1,
                                  "estado": "RESUELTA"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Equipo reparado"))
                .andExpect(jsonPath("$.estado").value("RESUELTA"));

        mockMvc.perform(post("/api/v1/solicitudes")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "descripcion": "",
                                  "clienteId": null
                                }
                                """))
                .andExpect(status().isBadRequest());

        mockMvc.perform(delete("/api/v1/solicitudes/1"))
                .andExpect(status().isNoContent());
    }
}
