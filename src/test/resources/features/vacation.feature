# Feature: Vacation registration
#     As an employee
#     I want to register vacation days
#     So that my unavailable days are tracked

#     Scenario: Employee registers a vacation day
#         Given a vacation employee with initials "HUBA"
#         When the employee registers vacation on 2026-07-13
#         Then the employee should have vacation on 2026-07-13

#     Scenario: Employee removes a vacation day
#         Given a vacation employee with initials "HUBA"
#         And the employee has vacation on 2026-07-13
#         When the employee removes vacation on 2026-07-13
#         Then the employee should not have vacation on 2026-07-13

#     Scenario: Employee registers the same vacation day twice
#         Given a vacation employee with initials "HUBA"
#         And the employee has vacation on 2026-07-13
#         When the employee registers vacation on 2026-07-13
#         Then the vacation error message "Vacation day already registered" is given
