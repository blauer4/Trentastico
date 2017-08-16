package com.geridea.trentastico.providers

import com.geridea.trentastico.logger.BugLogger
import com.geridea.trentastico.model.Course
import com.geridea.trentastico.model.Department

import java.util.ArrayList

/*
 * Created with ♥ by Slava on 03/03/2017.
 */

object DepartmentsProvider {

    val DEPARTMENTS = ArrayList<Department>()

    private fun load() {
        //Soluzione brutale, semplice, essenziale ma non sicura. In seguito può capitare che i corsi
        //cambino ad esempio se cambiano gli ID oppure nomi dei corsi o peggio ancora se se ne
        //aggiungeranno dei nuovi. La soluzione giusta sarebbe quella di utilizare questa lista come
        //base di partenza e di scaricarsi in background un elenco aggiornato ogni tot giorni.

        val dep1 = Department(10027, "Centro Interdipartimentale Biologia Integrata- CIBio")
        dep1.addCourse(Course(10116, "Laurea - Scienze e Tecnologie Biomolecolari - 0516G"))
        dep1.addCourse(Course(10232, "Laurea Magistrale - BIOTECNOLOGIE CELLULARI E MOLECOLARI - 0520H"))
        dep1.addCourse(Course(10616, "Laurea Magistrale - Biologia Quantitativa e Computazionale - 0521H"))
        DEPARTMENTS.add(dep1)


        val dep2 = Department(10030, "Centro interdipartimentale Mente/Cervello- CIMeC")
        dep2.addCourse(Course(10168, "Laurea Magistrale - Cognitive Science - Scienze Cognitive - 0708H"))
        DEPARTMENTS.add(dep2)


        val dep3 = Department(10019, "Dipartimento di Economia e Management")
        dep3.addCourse(Course(10126, "Laurea - Amministrazione Aziendale e Diritto - 0115G"))
        dep3.addCourse(Course(10128, "Laurea - Gestione Aziendale - 0116G"))
        dep3.addCourse(Course(10129, "Laurea - Economia e Management - 0117G"))
        dep3.addCourse(Course(10136, "Laurea Magistrale - International Management - Management Internazionale - 0119H"))
        dep3.addCourse(Course(10176, "Laurea Magistrale - Innovation Management - Management dell'innovazione - 0120H"))
        dep3.addCourse(Course(10178, "Laurea Magistrale - Economics - Economia - 0121H"))
        dep3.addCourse(Course(10179, "Laurea Magistrale - Finanza - 0122H"))
        dep3.addCourse(Course(10185, "Laurea Magistrale - Management - 0123H"))
        dep3.addCourse(Course(10186, "Laurea Magistrale - Management - 0124H"))
        dep3.addCourse(Course(10187, "Laurea Magistrale - Economia e legislazione d'impresa - 0125H"))
        dep3.addCourse(Course(10557, "aurea Magistrale - Management della sostenibilità e del turismo - 0126H"))
        DEPARTMENTS.add(dep3)


        val dep4 = Department(10025, "Dipartimento di Fisica")
        dep4.addCourse(Course(10113, "Laurea - Fisica - 0513G"))
        dep4.addCourse(Course(10169, "Laurea Magistrale - FISICA - 0518H"))
        DEPARTMENTS.add(dep4)


        val dep5 = Department(10021, "Dipartimento di Ingegneria Civile, Ambientale e Meccanica")
        dep5.addCourse(Course(10361, "Dottorato - Ingegneria civile, ambientale e meccanica - ICAM"))
        dep5.addCourse(Course(10127, "Laurea - Ingegneria Civile - 0325G"))
        dep5.addCourse(Course(10130, "Laurea - Ingegneria per l'ambiente e il territorio - 0326G"))
        dep5.addCourse(Course(10149, "Laurea Magistrale - Ingegneria Civile - 0331H"))
        dep5.addCourse(Course(10150, "Laurea Magistrale - Ingegneria per l'ambiente e il territorio - 0332H"))
        dep5.addCourse(Course(10437, "Laurea Magistrale - Ingegneria Energetica - 0337H"))
        dep5.addCourse(Course(10175, "Laurea Magistrale Ciclo Unico 5 anni - Ingegneria Edile-Architettura - 0336F"))
        DEPARTMENTS.add(dep5)


        val dep6 = Department(10023, "Dipartimento di Ingegneria e Scienza dell'Informazione")
        dep6.addCourse(Course(10114, "Laurea - Informatica - 0514G"))
        dep6.addCourse(Course(10133, "Laurea - Ingegneria Elettronica e delle Telecomunicazioni - 0329G"))
        dep6.addCourse(Course(10134, "Laurea - Ingegneria dell'informazione e Organizzazione D'impresa - 0330G"))
        dep6.addCourse(Course(10560, "Laurea - Ingegneria dell'Informazione e delle Comunicazioni - 0338G"))
        dep6.addCourse(Course(10627, "Laurea Magistrale - Ingegneria dell’Informazione e delle Comunicazioni - 0340H"))
        dep6.addCourse(Course(10153, "Laurea Magistrale - Ingegneria delle telecomunicazioni - 0335H"))
        dep6.addCourse(Course(10117, "Laurea Magistrale - INFORMATICA - 0517H"))
        DEPARTMENTS.add(dep6)


        val dep7 = Department(10022, "Dipartimento di Ingegneria Industriale")
        dep7.addCourse(Course(10131, "Laurea - Ingegneria Industriale - 0327G"))
        dep7.addCourse(Course(10151, "Laurea Magistrale - Ingegneria Meccatronica - 0333H"))
        dep7.addCourse(Course(10563, "Laurea Magistrale - Materials and production Engineering - Ingegneria dei materiali e della produzione - 0339H"))
        DEPARTMENTS.add(dep7)


        val dep8 = Department(10024, "Dipartimento di Lettere e Filosofia")
        dep8.addCourse(Course(10155, "Laurea - Filosofia - 0416G"))
        dep8.addCourse(Course(10156, "Laurea - Beni culturali - 0417G"))
        dep8.addCourse(Course(10158, "Laurea - Studi storici e filologico-letterari - 0419G"))
        dep8.addCourse(Course(10438, "Laurea - Lingue moderne - 0427G"))
        dep8.addCourse(Course(10164, "Laurea Magistrale - Letterature euroamericane, traduzione e critica letteraria - 0422H"))
        dep8.addCourse(Course(10165, "Laurea Magistrale - Mediazione linguistica, turismo e culture - 0423H"))
        dep8.addCourse(Course(10166, "Laurea Magistrale - Filologia e critica letteraria - 0424H"))
        dep8.addCourse(Course(10167, "Laurea Magistrale - Filosofia e linguaggi della modernità - 0420H"))
        dep8.addCourse(Course(10233, "Laurea Magistrale - Scienze storiche - 0426H"))
        DEPARTMENTS.add(dep8)


        val dep9 = Department(10026, "Dipartimento di Matematica")
        dep9.addCourse(Course(10115, "Laurea - Matematica - 0515G"))
        dep9.addCourse(Course(10170, "Laurea Magistrale - MATEMATICA - 0519H"))
        DEPARTMENTS.add(dep9)


        val dep10 = Department(10029, "Dipartimento di Psicologia e Scienze Cognitive")
        dep10.addCourse(Course(10112, "Laurea - Interfacce e Tecnologie della Comunicazione - 0704G"))
        dep10.addCourse(Course(10123, "Laurea - Scienze e Tecniche di Psicologia Cognitiva - 0705G"))
        dep10.addCourse(Course(10124, "Laurea Magistrale - Psicologia - 0707H"))
        dep10.addCourse(Course(10559, "Laurea Magistrale - Human-Computer Interaction - Interazione Persona-Macchina - 0709H"))
        DEPARTMENTS.add(dep10)


        val dep11 = Department(10028, "Dipartimento di Sociologia e Ricerca Sociale")
        dep11.addCourse(Course(10507, "Master di Secondo Livello - Previsione Sociale - M218"))
        dep11.addCourse(Course(10565, "Laurea - Studi internazionali - 0620G"))
        dep11.addCourse(Course(10624, "Laurea - Servizio sociale - 0622G"))
        dep11.addCourse(Course(10137, "Laurea - Sociologia - 0611G"))
        dep11.addCourse(Course(10138, "Laurea - Studi internazionali - 0612G"))
        dep11.addCourse(Course(10139, "Laurea - Servizio Sociale - 0613G"))
        dep11.addCourse(Course(10234, "Laurea Magistrale - Gestione delle organizzazioni e del territorio - 0618H"))
        dep11.addCourse(Course(10235, "Laurea Magistrale - Metodologia, Organizzazione e Valutazione dei Servizi Sociali - 0619H"))
        dep11.addCourse(Course(10567, "Laurea Magistrale - Sociology and social research - Sociologia e ricerca sociale - 0621H"))
        DEPARTMENTS.add(dep11)


        val dep12 = Department(10031, "Scuola di studi Internazionali")
        dep12.addCourse(Course(10177, "Laurea Magistrale - EUROPEAN AND INTERNATIONAL STUDIES - STUDI EUROPEI E INTERNAZIONALI  - 0803H"))
        dep12.addCourse(Course(10615, "Laurea Magistrale - Studi sulla Sicurezza Internazionale - 0804H"))
        DEPARTMENTS.add(dep12)

    }

    fun loadIfNeeded() {
        if (DEPARTMENTS.size == 0) {
            load()
        }
    }

    fun getDepartmentPosition(departmentId: Long): Int {
        for (i in DEPARTMENTS.indices) {
            if (DEPARTMENTS[i].id.toLong() == departmentId) {
                return i
            }

        }

        val exception = RuntimeException("UNKNOWN DEPARTMENT ID: " + departmentId)
        BugLogger.logBug("Could not get position of department with id " + departmentId, exception)
        throw exception
    }

    fun getDepartmentWithId(departmentId: Long): Department {
        for (i in DEPARTMENTS.indices) {
            val department = DEPARTMENTS[i]
            if (department.id.toLong() == departmentId) {
                return department
            }

        }

        val exception = RuntimeException("UNKNOWN DEPARTMENT WITH ID: " + departmentId)
        BugLogger.logBug("Could not find department with id " + departmentId, exception)
        throw exception
    }
}
