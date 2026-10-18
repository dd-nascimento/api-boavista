package com.david.api_boavista.service;

import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.david.api_boavista.dto.PedidoDTO;
import com.david.api_boavista.dto.PedidoResponseDTO;
import com.david.api_boavista.entities.Usuario;
import com.david.api_boavista.enums.StatusPedido;
import com.david.api_boavista.enums.CategoriaPedido;
import com.david.api_boavista.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import com.david.api_boavista.entities.Pedido;
import com.david.api_boavista.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final SequenciaPedidoService sequenciaPedidoService;
    private final UsuarioRepository usuarioRepository;

    private String gerarNumeroPedido(
        CategoriaPedido categoria,
        LocalDateTime dataCriacao) {

    String prefixo = switch (categoria) {
        case PROJETO -> "PROJ";
        case FORCA_TAREFA -> "FTA";
    };

    String data = dataCriacao.format(
            DateTimeFormatter.ofPattern("yyyyMMdd"));

    Long sequencia = sequenciaPedidoService
            .obterProximoNumero(dataCriacao.toLocalDate());

    return "%s-%s%06d".formatted(prefixo, data, sequencia);
    }

    private Usuario buscarUsuarioAutenticado() {
    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String email = authentication.getName();

    return usuarioRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("Usuário autenticado não encontrado"));
    }

    private PedidoResponseDTO converterParaDTO(Pedido pedido) {
    PedidoResponseDTO dto = new PedidoResponseDTO();

    dto.setId(pedido.getId());
    dto.setNumeroPedido(pedido.getNumeroPedido());
    dto.setCategoriaPedido(pedido.getCategoriaPedido());
    dto.setDataCriacao(pedido.getDataCriacao());
    dto.setDataHoraInicio(pedido.getDataHoraInicio());
    dto.setDataHoraFim(pedido.getDataHoraFim());
    dto.setLocalExecucao(pedido.getLocalExecucao());
    dto.setTitulo(pedido.getTitulo());
    dto.setDescricao(pedido.getDescricao());
    dto.setStatusPedido(pedido.getStatusPedido());

    dto.setSolicitanteNome(
            pedido.getUsuarioSolicitante().getNome());
    dto.setSolicitanteEmail(
            pedido.getUsuarioSolicitante().getEmail());

    if (pedido.getUsuarioSolucionador() != null) {
        dto.setSolucionadorNome(
                pedido.getUsuarioSolucionador().getNome());
        dto.setSolucionadorEmail(
                pedido.getUsuarioSolucionador().getEmail());
    }

    return dto;
    }

    @Transactional
    public PedidoResponseDTO criar(PedidoDTO dto) {
        Usuario solicitante = buscarUsuarioAutenticado();
        LocalDateTime agora = LocalDateTime.now();

        if (dto.getDataHoraInicio().isBefore(agora)) {
            throw new RuntimeException(
                    "A data de início não pode ser anterior à data de criação");
        }

        if (!dto.getDataHoraFim().isAfter(dto.getDataHoraInicio())) {
            throw new RuntimeException(
                    "A data de término deve ser posterior à data de início");
        }

        Pedido pedido = new Pedido();

        pedido.setNumeroPedido(
            gerarNumeroPedido(dto.getCategoriaPedido(), agora));
        pedido.setCategoriaPedido(dto.getCategoriaPedido());
        pedido.setDataCriacao(agora);
        pedido.setDataHoraInicio(dto.getDataHoraInicio());
        pedido.setDataHoraFim(dto.getDataHoraFim());
        pedido.setLocalExecucao(dto.getLocalExecucao());
        pedido.setTitulo(dto.getTitulo());
        pedido.setDescricao(dto.getDescricao());
        pedido.setUsuarioSolicitante(solicitante);
        pedido.setStatusPedido(StatusPedido.ABERTO);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return converterParaDTO(pedidoSalvo);
    }

    public List<PedidoResponseDTO> listarTodos() {
    return pedidoRepository.findAll()
            .stream()
            .map(this::converterParaDTO)
            .toList();
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pedido não encontrado"));

        return converterParaDTO(pedido);
    }

}