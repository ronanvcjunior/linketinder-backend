interface CandidatoDomain {
    id: number;
    nome: string;
    email: string;
    cpf: string;
    pais: string;
    estado: string;
    cep: string;
    descricao: string;
    competenciasId: number[];
}

export default CandidatoDomain;