package com.devhouse.core.services.cellchurch;

import com.devhouse.config.exception.DomainException;
import com.devhouse.core.ports.inbound.cellchurch.DeleteCellChurchInboundPort;
import com.devhouse.core.ports.outbound.CellChurchRepository;
import com.devhouse.core.ports.outbound.MemberRepository;

public class DeleteCellChurchService implements DeleteCellChurchInboundPort {

    private final CellChurchRepository cellChurchRepository;
    private final MemberRepository memberRepository;

    public DeleteCellChurchService(CellChurchRepository cellChurchRepository, MemberRepository memberRepository) {
        this.cellChurchRepository = cellChurchRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void execute(Long id) {
        if (memberRepository.countByCellChurchId(id) > 0) {
            throw new DomainException("Não é possível excluir a célula pois existem membros vinculados a ela.");
        }
        cellChurchRepository.delete(id);
    }
}
