package com.bigr.meumelhoramigo.config;

import com.bigr.meumelhoramigo.modelo.Animal;
import com.bigr.meumelhoramigo.modelo.AnimalEncontrado;
import com.bigr.meumelhoramigo.modelo.AnimalPerdido;
import com.bigr.meumelhoramigo.modelo.StatusAdocao;
import com.bigr.meumelhoramigo.modelo.StatusAnimal;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bean utilitario exposto aos templates Thymeleaf como "view".
 * Centraliza as regras de apresentacao do design system (especies, status,
 * avatar, datas) que no prototipo React viviam no store/ui.
 *
 * Uso no template: ${@view.speciesLabel(animal.especie)}
 */
@Component("view")
public class ViewHelper {

    private static final DateTimeFormatter BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /* ---------------- especies ---------------- */

    /** Normaliza a especie guardada (livre) para um id canonico. */
    public String speciesId(String especie) {
        if (especie == null) return "outro";
        String n = Normalizer.normalize(especie, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .trim();
        switch (n) {
            case "cao":
            case "cachorro":
                return "cao";
            case "gato":
                return "gato";
            case "passaro":
            case "ave":
                return "passaro";
            case "coelho":
                return "coelho";
            default:
                return "outro";
        }
    }

    public String speciesLabel(String especie) {
        switch (speciesId(especie)) {
            case "cao":     return "Cão";
            case "gato":    return "Gato";
            case "passaro": return "Pássaro";
            case "coelho":  return "Coelho";
            default:        return "Outro";
        }
    }

    public String speciesGlyph(String especie) {
        switch (speciesId(especie)) {
            case "cao":     return "🐶";
            case "gato":    return "🐱";
            case "passaro": return "🐦";
            case "coelho":  return "🐰";
            default:        return "🐾";
        }
    }

    public String speciesPhA(String especie) {
        switch (speciesId(especie)) {
            case "cao":     return "oklch(0.93 0.04 60)";
            case "gato":    return "oklch(0.93 0.03 30)";
            case "passaro": return "oklch(0.92 0.04 220)";
            case "coelho":  return "oklch(0.93 0.03 320)";
            default:        return "oklch(0.93 0.03 150)";
        }
    }

    public String speciesPhB(String especie) {
        switch (speciesId(especie)) {
            case "cao":     return "oklch(0.96 0.02 90)";
            case "gato":    return "oklch(0.96 0.02 50)";
            case "passaro": return "oklch(0.96 0.02 200)";
            case "coelho":  return "oklch(0.96 0.02 340)";
            default:        return "oklch(0.96 0.02 130)";
        }
    }

    /* ---------------- nome / tipo ---------------- */

    /** Nome para listagem: nome do perdido, "Sem nome" se encontrado, senão "—". */
    public String nome(Animal a) {
        if (a instanceof AnimalPerdido p && p.getNome() != null && !p.getNome().isBlank()) {
            return p.getNome();
        }
        return a.getStatus() == StatusAnimal.ENCONTRADO ? "Sem nome" : "—";
    }

    /** Nome para o título do detalhe (fallback "Animal sem nome"). */
    public String nomeOuPadrao(Animal a) {
        if (a instanceof AnimalPerdido p && p.getNome() != null && !p.getNome().isBlank()) {
            return p.getNome();
        }
        return "Animal sem nome";
    }

    public boolean isPerdido(Animal a) {
        return a instanceof AnimalPerdido;
    }

    /** Texto usado na busca client-side (nome + raça + cor + local), minúsculo. */
    public String searchKey(Animal a) {
        StringBuilder sb = new StringBuilder();
        sb.append(nome(a)).append(' ');
        if (a.getRaca() != null) sb.append(a.getRaca()).append(' ');
        if (a.getCor() != null) sb.append(a.getCor()).append(' ');
        if (a.getLocal() != null) sb.append(a.getLocal());
        return sb.toString().toLowerCase();
    }

    public boolean isEncontrado(Animal a) {
        return a instanceof AnimalEncontrado;
    }

    /* ---------------- campos especificos (detalhe) ---------------- */

    public String caracteristicas(Animal a) {
        return a instanceof AnimalPerdido p ? p.getCaracteristicas() : null;
    }

    public String recompensa(Animal a) {
        return a instanceof AnimalPerdido p ? p.getRecompensa() : null;
    }

    public String dataDesaparecimento(Animal a) {
        return (a instanceof AnimalPerdido p && p.getDataDesaparecimento() != null)
                ? p.getDataDesaparecimento().format(BR) : null;
    }

    public String saude(Animal a) {
        return a instanceof AnimalEncontrado e ? e.getCondicaoSaude() : null;
    }

    public String localEncontro(Animal a) {
        return a instanceof AnimalEncontrado e ? e.getLocalEncontro() : null;
    }

    public String dataEncontro(Animal a) {
        return (a instanceof AnimalEncontrado e && e.getDataEncontro() != null)
                ? e.getDataEncontro().format(BR) : null;
    }

    /* ---------------- status da solicitacao de adocao ---------------- */

    public String adocaoLabel(StatusAdocao s) {
        if (s == null) return "";
        switch (s) {
            case PENDENTE:  return "Pendente";
            case APROVADO:  return "Aprovado";
            case RECUSADO:  return "Recusado";
            case CONCLUIDO: return "Concluído";
            default:        return s.name();
        }
    }

    public String adocaoClass(StatusAdocao s) {
        if (s == null) return "";
        switch (s) {
            case PENDENTE:  return "enc";
            case APROVADO:  return "disp";
            case RECUSADO:  return "perd";
            case CONCLUIDO: return "adot";
            default:        return "";
        }
    }

    /* ---------------- status ---------------- */

    public String statusLabel(StatusAnimal status) {
        if (status == null) return "";
        switch (status) {
            case DISPONIVEL: return "Disponível";
            case PERDIDO:    return "Perdido";
            case ENCONTRADO: return "Encontrado";
            case ADOTADO:    return "Adotado";
            default:         return status.name();
        }
    }

    public String statusClass(StatusAnimal status) {
        if (status == null) return "";
        switch (status) {
            case DISPONIVEL: return "disp";
            case PERDIDO:    return "perd";
            case ENCONTRADO: return "enc";
            case ADOTADO:    return "adot";
            default:         return "";
        }
    }

    /* ---------------- avatar ---------------- */

    private static final String[] AV_COLORS = {
            "oklch(0.62 0.15 38)", "oklch(0.58 0.12 240)", "oklch(0.55 0.12 152)",
            "oklch(0.55 0.13 300)", "oklch(0.6 0.13 70)", "oklch(0.56 0.15 20)"
    };

    public String avatarColor(String name) {
        String n = (name == null || name.isEmpty()) ? "?" : name;
        return AV_COLORS[n.charAt(0) % AV_COLORS.length];
    }

    public String initials(String name) {
        if (name == null || name.isBlank()) return "?";
        String[] parts = name.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length && sb.length() < 2; i++) {
            if (!parts[i].isEmpty()) sb.append(parts[i].charAt(0));
        }
        return sb.toString().toUpperCase();
    }

    /* ---------------- datas ---------------- */

    public String fmtDate(LocalDate d) {
        return d == null ? "—" : d.format(BR);
    }

    public String fmtDateTime(LocalDateTime d) {
        return d == null ? "—" : d.format(BR);
    }
}
