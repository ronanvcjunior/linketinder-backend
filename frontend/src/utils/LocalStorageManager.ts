class LocalStorageManager {
    constructor() {
        this.initializeLocalStorage();
    }

    private initializeLocalStorage(): void {
        const localStorageEmpresas: (string|null) = localStorage.getItem('empresas');
        const localStorageCandidatos: (string|null) = localStorage.getItem('candidatos');
        const localStorageVagas: (string|null) = localStorage.getItem('vagas');
        const localStorageCompetencias: (string|null) = localStorage.getItem('competencias');

        if (!localStorageEmpresas) {
            import("../file/empresas.json")
                .then((module): void => {
                    localStorage.setItem('empresas', JSON.stringify(module.default));
                    console.log('Dados salvos no localStorage empresas.');
                });
        }

        if (!localStorageCandidatos) {
            import("../file/candidatos.json")
                .then((module): void => {
                    localStorage.setItem('candidatos', JSON.stringify(module.default));
                    console.log('Dados salvos no localStorage candidatos.');
                });
        }

        if (!localStorageVagas) {
            import("../file/vagas.json")
                .then((module): void => {
                    localStorage.setItem('vagas', JSON.stringify(module.default));
                    console.log('Dados salvos no localStorage vagas.');
                });
        }

        if (!localStorageCompetencias) {
            import("../file/competencias.json")
                .then((module): void => {
                    localStorage.setItem('competencias', JSON.stringify(module.default));
                    console.log('Dados salvos no localStorage competencias.');
                });
        }
    }
}

export default LocalStorageManager;
