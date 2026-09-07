// worker.js - Precise Simulator for AndroidX SQLite Web
console.log("Database Engine (v4) starting...");

const statements = new Map();
let nextId = 1;

self.onmessage = function(e) {
    const { id, type, payload } = e.data;

    // Logghiamo per debug: lo vedrai nella console F12
    console.log(`[Worker] Req: ${type} (ID: ${id})`);

    let result = {};

    try {
        switch (type) {
            case 'open':
                // Room si aspetta questo oggetto esatto
                result = {
                    connectionId: 1,
                    readOnly: false,
                    path: payload.name || "manapp.db"
                };
                break;

            case 'prepare':
                const stmtId = nextId++;
                statements.set(stmtId, {
                    sql: payload.sql,
                    readDone: false
                });
                result = { statementId: stmtId };
                break;

            case 'bind':
                // Conferma semplice del binding
                result = {};
                break;

            case 'step':
                const st = statements.get(payload.statementId);
                let hasRow = false;
                // Se è una query di lettura (SELECT), restituiamo una riga finta la prima volta
                if (st && st.sql.toUpperCase().includes("SELECT")) {
                    if (!st.readDone) {
                        hasRow = true;
                        st.readDone = true;
                        st.currentRow = ["mock-id", 0, "Op", "Prod", 0];
                    }
                }
                result = { rowApplied: hasRow };
                break;

            case 'column':
                const stmt = statements.get(payload.statementId);
                // Restituiamo 0 o stringa per evitare crash di cast in Kotlin
                let val = 0;
                if (payload.index === 0) val = "mock-id-1";
                result = { value: val };
                break;

            case 'close':
                if (payload.statementId) statements.delete(payload.statementId);
                result = {};
                break;

            default:
                result = {};
        }

        // Risposta standard al driver Kotlin
        postMessage({
            id: id,
            result: result,
            error: null
        });

    } catch (err) {
        console.error("Worker Error:", err);
        postMessage({ id: id, result: null, error: err.message });
    }
};